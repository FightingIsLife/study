package com.jrymos.study.framework.nio;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class MyNioClient implements Runnable, Closeable {

    private final Selector selector;
    private volatile boolean stop;
    private final SocketChannel socketChannel;

    public MyNioClient(SocketAddress socketAddress) throws IOException {
        selector = Selector.open();
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(socketAddress);
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    @Override
    public void close() throws IOException {
        this.stop = true;
        selector.close();
        socketChannel.close();
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();

                    if (!selectionKey.isValid()) {
                        continue;
                    }

                    if (selectionKey.isConnectable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        if (channel.finishConnect()) {
                            channel.register(selector, SelectionKey.OP_READ);
                            System.out.println("Connected to server!");
                        }
                    }

                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int read = channel.read(byteBuffer);
                        if (read > 0) {
                            byteBuffer.flip();
                            byte[] bytes = new byte[byteBuffer.remaining()];
                            byteBuffer.get(bytes);
                            String res = new String(bytes, StandardCharsets.UTF_8);
                            System.out.println(res);
                        }
                        if (read < 0) {
                            selectionKey.cancel();
                            channel.close();
                        }
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void write(String s) throws IOException {
        ByteBuffer wrap = ByteBuffer.wrap((s + "\r\n\r\n").getBytes(StandardCharsets.UTF_8)); // 确保以 \r\n\r\n 结束
        socketChannel.write(wrap);
        System.out.println(s);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        MyNioClient myNioClient = new MyNioClient(new InetSocketAddress("i-test-sg.famoapp.com", 80));
        new Thread(myNioClient).start();
        Thread.sleep(1000);
        myNioClient.write("""
                GET /room/ping HTTP/1.1
                Host: i-test-sg.famoapp.com
                Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
                Accept-Language: zh-CN,zh;q=0.9
                Connection: keep-alive
                Upgrade-Insecure-Requests: 1
                User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36
                """.trim());
    }
}
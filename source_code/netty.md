java网络编程：
	tcp 面向连接：
		短连接
		长连接
	网络通讯流程：
		连接，读数据，写数据
	
	socket：
		bio
		nio
		aio
	
高效的rpc框架：
1.序列化
2.服务的注册、发现
3.服务负载均衡

典型：dubbo、spring cloud


netty 取代jdk里面的nio调用


阻塞：
	1.数据未到达-进行读
	2.缓冲区满-进行写
非阻塞： 以上情况直接返回

同步： 数据就绪-程序自己去读
异步： 数据就绪-回调函数回调程序


jdk中的nio

bio: thread <-> socket <-> 客户端
nio: thread -> selector ->>> buffer <-> 客户端

ServerSelectorChannel
Selector
SocketChannel register

nio三大核心组件： selector channel buffer

reactor三种版本：
单线程
多线程
主从多线程


堆内存和堆外内存

Socket ServerSocket

javax  org.apache


Servlet


HttpServlet

容器所做的事情：
1. 将inputStream 转成 ServletRequest
2. 将ServletResponse 转成 OutputStream







server：
	Service：
		connector：
			protocol:
			port:
		engine：
			host:
				context： 对应一个web服务
			listener

Lifecycle:
	new init start stop destroy 
		
 发布事件		initing inited
		
LifecycleBase:

LifecycleListener


Service： engine  connector


bootstrap 启动

catalina
	load -> init
	start -> start

CatalinaShutdownHook


Connector
	ProtocolHandler
		AbstractEndpoint
			nio版本  Nio2Endpoint
			socket版本 JIoEndpoint
			Acceptor
		Executor
		Processor
	Adpter

		

StandardContext

Container

Wrapper 等价于 Servlet

StandardWrapper load

ContextConfig

WebXml


StandardManager 


ApplicationFilterChain


Container: engine host context wrapper

findChildren
StartChild


HostConfig

多个Context 有相同的class，自定义类加载器

tomcat类加载器：


类加载器过程：
Demo.java -> Demo.class

loading -> verification -> preparation -> resolution -> initialization

-> Class对象


Bootstrap ClassLoader
自定义类加载器


bootstrap extension system
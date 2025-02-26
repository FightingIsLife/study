
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
				context：
			

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
	

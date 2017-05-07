
function myRefersh( e ) {
	
	const source = e.src ; // 获得原来的 src 中的内容
	//console.log( "source : " + source  ) ;
	
	var index = source.indexOf( "?" ) ;  // 从 source 中寻找 ? 第一次出现的位置 (如果不存在则返回 -1 )
	//console.log( "index : " + index  ) ;
	
	if( index > -1 ) { // 如果找到了 ?  就进入内部
		var s = source.substring( 0 , index ) ; // 从 source 中截取 index 之前的内容 ( index 以及 index 之后的内容都被舍弃 )
		//console.log( "s : " + s  ) ;
		
		var date = new Date(); // 创建一个 Date 对象的 一个 实例
		var time = date.getTime() ; // 从 新创建的 Date 对象的实例中获得该时间对应毫秒值
		e.src = s + "?time=" + time ; // 将 加了 尾巴 的 地址 重新放入到 src 上
		
		//console.log( e.src ) ;
	} else {
		var date = new Date();
		e.src = source + "?time=" + date.getTime();
	}
	
}
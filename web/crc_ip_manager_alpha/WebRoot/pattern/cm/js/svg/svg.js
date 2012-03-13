
function contextMenu(props,events){
   this.props = props;
   this.events = events;
}

contextMenu.prototype.buildContextMenu = function(){
	 // 加载右键菜单体
   var menuObj = document.getElementById(this.props.menuID);
   
   // 提取菜单初始化设置的属性及事件
   var targetEle = this.props.targetEle;
   var eventFunc = this.events;
   
   // 初始化菜单项的mouseover 和 mouseout事件
   var _items = menuObj.getElementsByTagName("li");
  	for(var i=0;i<_items.length;i++){
  		if(_items[i].className != "separator"){
  			 _items[i].className = "item";
	  		 _items[i].onmouseover = function(){this.className ="item itemOver";};
	  		 _items[i].onmouseout = function(){this.className = "item";}
  	  }
  }

   document.oncontextmenu = function(evt){
   	     var _bodyWidth = null;
		 var _bodyHeight = null;
		 var _mWidth = null;
		 var _mHeight = null;
		 var _px = null;
		 var _py = null;
		 var _offtop = null;
	 	 try{
				  var cobj = ele(evt);
				  if(cobj.id){
				  	// 绑定菜单项点击事件
			  	  for(var j=0;j<_items.length;j++){
			  		  if(_items[j].className != "separator"){
				  		 _items[j].onclick = function(){hide();func(this.id,cobj);};
			  	    }
			  	  }
			  	
			  	// 判断显示位置
			  	_offtop = document.body.scrollTop;
			  	_px = parseInt(getX(evt));
			  	_py = parseInt(getY(evt));
			  	_bodyWidth = parseInt(document.body.offsetWidth ||document.body.clientWidth);
			  	_bodyHeight = parseInt(document.body.offsetHeight || document.body.clientHeight);
			  	_mWidth = parseInt(menuObj.style.width);
			  	_mHeight = parseInt(menuObj.offsetHeight);
			  	menuObj.style.left = ((_px + _mWidth) > _bodyWidth?(_px - _mWidth):_px) +"px";
				menuObj.style.top  = ((_py + _mHeight) > _bodyHeight?(_py - _mHeight + _offtop):(_py + _offtop)) +"px";
				menuObj.style.display = "block";
			  }else{
			    hide();	
			  }
		  }catch(e){
		  }finally{
		  	_bodyWidth = null;
			  _bodyHeight = null;
			  _mWidth = null;
			  _mHeight = null;
			  _px = null;
		    _py = null;
		  	clearEventBubble(evt);
		  	return false;	
		  }
	  
 }
 
 document.onclick = function(){hide();}
  
 var func = function(fid,srcEle){
  		eventFunc.bindings[fid](srcEle);
  }
  
 var hide = function(){
   	 try{
  		 if(menuObj && menuObj.style.display != "none"){
  		 		menuObj.style.display = "none";
  		 	}
  	}catch(e){}
  }
 var ele = function(evt){
      evt = evt||window.event;
      var _ele = null;
      try{
      	_ele = (evt.srcElement || evt.target);
      	return _ele;
      }catch(e){
      }finally{
      	_ele = null;
      }
   }

}


/*==============================================================*/
 	function getX(evt){
 		  var _x = null;
		 	try{
		 			evt = evt || window.event;
		 			_x = (evt.x || evt.clientX || evt.pageX);
		 			return _x;
		 	}catch(e){
		 	}finally{
		 		_x = null;
		 	}
		
	}
	
	function getY(evt){
			var _y = null;
		 	try{
		 			evt = evt || window.event;
		 			_y = (evt.y || evt.clientY || evt.pageY);
		 			return _y;
		 	}catch(e){
		 	}finally{
		 		_y = null;
		 	}
	}
	
	function clearEventBubble(evt){
	   evt = evt || window.event;
	   
	   if(evt.stopPropagation){
	   	 evt.stopPropagation();
	   }else{
	     evt.cancelBubble = true; 
	   }
	   
    if(evt.preventDefault){
    	 evt.preventDefault();
    }else{ 
       evt.returnValue = false;
    }

	} 

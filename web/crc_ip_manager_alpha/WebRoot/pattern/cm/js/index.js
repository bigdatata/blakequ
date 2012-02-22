// JavaScript Document
function $(item){return document.getElementById(item);}
function changeselects(n)
{
	
	for(var i=1;i<4; i++)
	{
	if(i==n)
	$("t"+i).style.display="block";
	else
	$("t"+i).style.display="none";
	}
}
function changeclassname(obj)
{
	
	if (obj.className=="tit_1")
	obj.className="tit_2";
	  
	else
	  obj.className="tit_1";
}
function changeleft_nav(item1,item2)
{
    var id=$(item1);
	var id2=$(item2);
	if(id2==null)
	{
       if(id.style.display=="block")
	  {
	    id.style.display="none";
	    
	  }
	  else 
	  {
	   id.style.display="block";
	 
	  }
	}
  else
  {
	
	if(id.style.display=="block")
	{
	  id.style.display="none";
	  id2.className="tit_2_1";
	}
	else 
	{
	  id.style.display="block";
	  id2.className="tit_2_2";
	}
  }
	  
}

function display(obj){
  var opt=obj.options[obj.selectedIndex];
  for(var i=1;i<7;i++){
    if(opt.value==1)
	  window.location='acceptance_data.html';
	else if(opt.value==2)
	  window.location='acceptance_initial.html';
	  else if(opt.value==3)
	  window.location='acceptance_final.html';  
	  else if(opt.value==4)
	  window.location='acceptance_operation.html';  
	  else if(opt.value==5)
	  window.location='acceptance_move.html';  
	  else if(opt.value==6)
	  window.location='acceptance_archives.html';  
  }
}
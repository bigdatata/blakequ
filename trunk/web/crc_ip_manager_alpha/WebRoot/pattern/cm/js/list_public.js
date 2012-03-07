// JavaScript Document
function getId(obj){return document.getElementById(obj);}
function  display(obj){
  
  var opt = obj.options[obj.selectedIndex]
	  if(opt.value==1){

			  getId('search'+1).style.display="";
			  getId('search'+2).style.display="none";
			  getId('search'+3).style.display="none";
              
	  }
	  else if(opt.value==2){
		      getId('search'+1).style.display="none";
			  getId('search'+2).style.display="";
			  getId('search'+3).style.display="none";
         
	  }
	  else if(opt.value==3){
		   
		     getId('search'+1).style.display="none";
			  getId('search'+2).style.display="none";
			  getId('search'+3).style.display="";
              
	  }
}
function display_search(){
	var search = getId('search');
	if(search.style.display==''){
	  search.style.display="none";
	}
    else{
	  search.style.display="";
	}
}
$(document).ready(function(){
         $(".flexme1").flexigrid({height: 455});
		});
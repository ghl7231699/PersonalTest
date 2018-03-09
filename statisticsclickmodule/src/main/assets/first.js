
function outJs()
{
	document.getElementById("out").innerHTML="这是外部的script";
}
// window.alert("Warnning");
function add(a,b){
	return a*b
}
function add(){
	document.getElementById("add").innerHTML=10*10;
}
document.getElementById("text").innerHTML="Hello World";

function getSomething(name,job){
	alert("Welcome"+name+",the"+job);
}
function openNewWindow(name, url, width, height, posX, posY) {
	eval(name + " = window.open('" + url + "','" + name + "','width=" + width + ",height=" + height + ",toolbar=no,status=no,resizabe=no')");
	eval(name + ".focus()");
	eval(name + ".moveTo(" + posX + "," + posY + ")");
}

function f() {
    var vv=document.getElementById('lang');
    if(vv.options[vv.selectedIndex].text==="EN"){
        vv.style="background: url('../img/us.png') no-repeat white;font-weight: bolder;"
    } else if(vv.options[vv.selectedIndex].text==="RU"){
        vv.style="background: url('../img/rus.png') no-repeat white;font-weight: bolder;"
    } else if(vv.options[vv.selectedIndex].text==="UA"){
        vv.style="background: url('../img/ua.png') no-repeat white;font-weight: bolder;"
    }

}
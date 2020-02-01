function selectorName() {
    var name=document.getElementById('srtName');
    var place=document.getElementById('srtPlace');
    var free=document.getElementById('srtFreePlace');

    name.style="display:block";
    place.style="display:none";
    free.style="display:none";
}
function selectorPlace() {
    var name=document.getElementById('srtName');
    var place=document.getElementById('srtPlace');
    var free=document.getElementById('srtFreePlace');

    name.style="display:none";
    place.style="display:block";
    free.style="display:none";
}
function selectorFreePlace() {
    var name=document.getElementById('srtName');
    var place=document.getElementById('srtPlace');
    var free=document.getElementById('srtFreePlace');

    name.style="display:none";
    place.style="display:none";
    free.style="display:block";
}
//fucktion for showing fields to change subject mark
function showFields() {
    var sub=document.getElementsByClassName('subject');
    var attNumb=document.getElementById('number');
    var i=0;

    attNumb.style="display:block";
    while (i<sub.length){
        sub[i].style="display:block";
        i++;
    }
    return false;

}
function valNumb() {
    var sub=document.getElementsByClassName('subject');
    var attNumb=document.getElementById('number');
    var notiff=document.getElementById('atestatNotiffication');
    var attNumber=document.getElementById('number');



    var i=0;
    var re=/\D/g;

    if(attNumb.value.match(re)||attNumb.value===""||attNumb.value.length>9){
        notiff.style="display:block";
        return false;
    }

    while (i<sub.length){
        if(sub[i].value.match(re)||sub[i].value===""||parseInt(sub[i].value)<0||parseInt(sub[i].value)>12){
            notiff.style="display:block";
            return false;
        }
        i++;
    }



}

function validateAplication(form) {
    var aplicationNotif=document.getElementsByClassName('notif')[0];
    var atestatSubject=document.getElementsByClassName('subject');
    var sub=document.getElementsByTagName('select');
    // var zno=document.getElementsByClassName('znoHeadline');
    var zno=document.getElementsByClassName('subId');

    var file=document.getElementById('file');




    if(file!=null&&file.value===""){
        aplicationNotif.style="display:block";
        return false;
    }

    if(form.department.value==="..."){
        aplicationNotif.style="display:block";
        return false;
    }

    if(form.univers.value==="..."){
        aplicationNotif.style="display:block";
        return false;
    }

    var i=0;
    while (i<sub.length){
        if(sub[i].style.display==="block"||zno[i].textContent===""||zno[i].textContent==="...") {
            aplicationNotif.style="display:block";
            return false;
        }

        i++;
    }


}

function showZno() {
    var sub=document.getElementsByTagName('select');

    var i=0;
    while (i<sub.length){
        sub[i].style="display:block";
        i++;
    }
    return false;

}

function btValid(){
    var j=0;
    var aplicationNotif=document.getElementsByClassName('notif')[0];
    var mark=document.getElementsByClassName('atestatMark');

    while (j<mark.length){
        if(mark[j].textContent===""){
            aplicationNotif.style="display:block";
            return false;
        }
        j++;
    }
}

function fileValidation(form) {
    var file=document.getElementById('file');
    var notif=document.getElementsByClassName('scanNotif');

    if(file.value===""){
        notif[0].style="display:block";
        return false;
    }

}




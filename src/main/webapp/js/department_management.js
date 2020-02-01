/**
 * Created by admin on 09.01.2018.
 */




function showEditional() {
    var fields=document.getElementsByClassName('editional');
    var cancelBut=document.getElementById('cancelButton');
    var addBt=document.getElementById('addButton');
    var saveBt=document.getElementById('submitBt');
    var i=0;
    while (i<fields.length){
        fields[i].style="display:table-cell";
        i++;
    }
    cancelBut.style="display:inline";
    addBt.style="display:none";
    saveBt.style="display:inline-block";



    return false;
}

function cancelFunc() {
    location.reload(true);
    return false;
}

function validation() {
    var fields=document.getElementsByClassName('addFields');
    var notif=document.getElementById('notif');
    var notif2=document.getElementsByClassName('notif1');
    var re=/[\d]/g;
    var forb=/[~`!@#$%^&*?<>,.()0-9"';:]/g;
    var i=0;
    while (i<fields.length){
        if(fields[i].value===""){
            notif.style="display:block";
            return false;
        }
        i++;
    }


    if(!fields[0].value.match(re)||!fields[2].value.match(re)||!fields[3].value.match(re)){
        notif2[0].style="display:block";
        return false;
    }
    if(fields[0].value.length>9||fields[2].value.length>9||fields[3].value.length>9){
        notif2[3].style="display:block";
        return false;
    }
    if(parseInt(fields[0].value)<0||parseInt(fields[2].value)<0||parseInt(fields[3].value)<0){
        notif2[1].style="display:block";
        return false;
    }
    if(parseInt(fields[2].value)<parseInt(fields[3].value)){
        notif2[2].style="display:block";
        return false;
    }
    if(fields[1].value.length>30){
        notif2[4].style="display:block";
        return false;
    }

    if(fields[1].value.match(forb)){
        notif2[5].style="display:block";
        return false;
    }


}

function validateUpdate(form) {
    var notif=document.getElementById('notif');
    var negative=document.getElementById('notif5');
    var allLessfree=document.getElementById('notif6');
    var notif2=document.getElementsByClassName('notif1');
    var forb=/[~`!@#$%^&*?<>,.()0-9"';:]/g;
    var re=/[\d]/g;


    if(form.upName.value===""||form.upAll.value===""||form.upFree.value===""){
        notif.style="display:block";
        return false;
    }
    if(form.upAll.value.length>9||form.upFree.value.length>9){
        notif2[0].style="display:block";
        return false;
    }
    if(parseInt(form.upAll.value)<0||parseInt(form.upFree.value)<0){
        negative.style="display:block";
        return false;
    }

    if(parseInt(form.upAll.value)<parseInt(form.upFree.value)){
        allLessfree.style="display:block";
        return false;
    }
    if(form.upName.value.length>30){
        notif2[1].style="display:block";
        return false;
    }

    if(form.upName.value.match(forb)){
        notif2[2].style="display:block";
        return false;
    }
    if(!form.upAll.value.match(re) || !form.upFree.value.match(re)){
        notif2[3].style="display:block";
        return false;
    }





}


// var modal=document.getElementById('myModal');
//
// window.onclick=function () {
//     if(event.target==modal){
//         modal.style.display="none";
//     }
// }

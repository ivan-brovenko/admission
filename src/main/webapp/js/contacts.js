/**
 * Created by admin on 19.01.2018.
 */

function emailVal(form) {
    var notif=document.getElementsByClassName('notif');
    if(form.pass.value===""||form.subject.value===""||form.message.value===""){
        notif[0].style="display:block";
        return false;
    }
}

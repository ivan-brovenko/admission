function checkForm(form){

    var i=document.getElementsByClassName('wrongPassword')[2];
    // var re=/[\W]/;
    var mail=/[\w]+@[\w]+\.[\w]+/g;

    if(form.login.value===""||form.mypassword.value===""||!form.login.value.match(mail)){
        i.style="display:block";
        form.login.focus();
        return false;

    }

}

function forg() {
    var forg=document.getElementsByClassName('innerForg')[0];
    forg.style="display:block";

}
function loginVal(form) {
    var mail=/[\w]+@gmail.com/g;
    var notif=document.getElementsByClassName('wrongPassword')[3];

    if(!form.forgotLogin.value.match(mail)){
        notif.style="display:block";
        return false;
    }
}

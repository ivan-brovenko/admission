function validate(form) {
    var noMatch=document.getElementById('noMatch');
    var forbSymb=document.getElementById('forbSymb');
    var wrongEmail=document.getElementById('wrongEmail');
    var fillAll=document.getElementById('fillAll');
    var less8=document.getElementById('less8');

    var answer=document.getElementById('answer');
    var digit1=parseInt(document.getElementById('digit1').innerHTML);
    var digit2=parseInt(document.getElementById('digit2').innerHTML);
    var sum=digit1+digit2;
    var captchaNotif=document.getElementById('captchaNotif');

    // var fio=/[A-zÀ-ÿ0-9]/g;
    var mail=/[\w]+@gmail.com/g;
    var fio=/[~`!@#$%^&*?<>,.()0-9"';:]/g;
    var forb=/[~`!@#$%^&*?<>,.()"';:]/g;




    if(form.name.value==""||form.lastName.value==""||form.patronymic.value==""||
        form.email.value==""||form.city.value==""||form.region.value==""||
        form.schName.value==""||form.password.value==""||form.rpassword.value==""){
        fillAll.style="display:block";
        return false;
    }
    if(form.name.value.match(fio)||form.lastName.value.match(fio)||form.patronymic.value.match(fio)||
    form.city.value.match(fio)||form.region.value.match(fio)||form.schName.value.match(forb)){
        forbSymb.style="display:block";
        return false;

    }

    if(form.password.value!=form.rpassword.value){
        noMatch.style="display:block";
        return false;
    }

    if(!form.email.value.match(mail)){
        wrongEmail.style="display:block";
        return false;
    }

    if(form.password.value.length<8){
        less8.style="display:block";
        return false;
    }

    if(parseInt(answer.value)!==sum){
        captchaNotif.style="display:block";
        return false;
    }

    var allform=document.getElementsByClassName('form');
    var i=0;
    while (i<allform.length){
        if(allform[i].value.length>30){
            document.getElementById('less30').style="display:block";
            return false;
        }
        i++;
    }


    
}

function randNumb() {
    var randNumb1=Math.floor(Math.random()*10)+1;
    var randNumb2=Math.floor(Math.random()*10)+1;

    document.getElementById('digit1').innerHTML=randNumb1;
    document.getElementById('digit2').innerHTML=randNumb2;

}

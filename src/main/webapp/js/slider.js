autoSlide();
var temp=0;
var temp1;

function buttonLeft() {
    var l=document.getElementById('line');
    if(temp>-200)
        temp=temp-100;
    else temp=0;
    l.style.left=temp+'%';


}

function buttonRight() {
    var l=document.getElementById('line');
    if(temp<0)
        temp=temp+100;
    else temp=-200;
    l.style.left=temp+'%';


}
function autoSlide() {
    temp1= setTimeout(function () {
        var l=document.getElementById('line');
        if(temp>-200)
            temp=temp-100;
        else temp=0;
        l.style.left=temp+'%';
        autoSlide();

    },10000);

}








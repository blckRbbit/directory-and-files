let modal = document.getElementById("fade");
let elements = document.querySelectorAll(".btn__modal");
let dirs = document.querySelectorAll(".directory");
let span = document.getElementsByClassName("close")[0];
let getPathBtn = document.getElementById("getPath")
let pageNums = document.querySelectorAll(".pageLink");

for (let i = 0; i < elements.length; i++) {
  elements[i].onclick = function(){
    modal.style.display = "block";
  };
}

span.onclick = function() {
    modal.style.display = "none";
}

getPathBtn.onclick = function() {
    let path = document.querySelector(".form-control").value;
    if (path==null) {
        path="/"
    }
}

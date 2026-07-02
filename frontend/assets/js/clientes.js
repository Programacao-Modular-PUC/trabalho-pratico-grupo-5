const modal = document.querySelector(".modal");

const openBtn = document.getElementById("openModal");

const closeBtn = document.querySelector(".close");

const cancelBtn = document.querySelector(".btn-cancel");

openBtn.onclick = () => {

    modal.classList.add("active");

};

closeBtn.onclick = () => {

    modal.classList.remove("active");

};

cancelBtn.onclick = () => {

    modal.classList.remove("active");

};

window.onclick = (e)=>{

    if(e.target===modal){

        modal.classList.remove("active");

    }

};
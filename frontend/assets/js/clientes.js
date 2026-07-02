const modal = document.querySelector(".modal");

const openBtn = document.getElementById("openModal");

const closeBtn = document.querySelector(".close");

const cancelBtn = document.querySelector(".btn-cancel");

const modalTitle = document.getElementById("modalTitle");

const saveBtn = document.getElementById("saveBtn");

const nome = document.getElementById("nome");

const cpf = document.getElementById("cpf");

const telefone = document.getElementById("telefone");

const email = document.getElementById("email");

function abrirModal(){

    modal.classList.add("active");

}

function fecharModal(){

    modal.classList.remove("active");

}

openBtn.onclick=()=>{

    modalTitle.innerText="Novo Cliente";

    saveBtn.style.display="inline-block";

    nome.readOnly=false;
    cpf.readOnly=false;
    telefone.readOnly=false;
    email.readOnly=false;

    nome.value="";
    cpf.value="";
    telefone.value="";
    email.value="";

    abrirModal();

};

closeBtn.onclick=fecharModal;

cancelBtn.onclick=fecharModal;

window.onclick=(e)=>{

    if(e.target===modal){

        fecharModal();

    }

};

document.querySelectorAll(".view").forEach(btn=>{

    btn.onclick=function(){

        const tr=this.closest("tr");

        const dados=tr.querySelectorAll("td");

        modalTitle.innerText="Visualizar Cliente";

        nome.value=dados[0].innerText;
        cpf.value=dados[1].innerText;
        telefone.value=dados[2].innerText;
        email.value=dados[3].innerText;

        nome.readOnly=true;
        cpf.readOnly=true;
        telefone.readOnly=true;
        email.readOnly=true;

        saveBtn.style.display="none";

        abrirModal();

    }

});

document.querySelectorAll(".edit").forEach(btn=>{

    btn.onclick=function(){

        const tr=this.closest("tr");

        const dados=tr.querySelectorAll("td");

        modalTitle.innerText="Editar Cliente";

        nome.value=dados[0].innerText;
        cpf.value=dados[1].innerText;
        telefone.value=dados[2].innerText;
        email.value=dados[3].innerText;

        nome.readOnly=false;
        cpf.readOnly=false;
        telefone.readOnly=false;
        email.readOnly=false;

        saveBtn.style.display="inline-block";

        abrirModal();

    }

});

document.querySelectorAll(".delete").forEach(btn=>{

    btn.onclick=function(){

        if(confirm("Deseja excluir este cliente?")){

            this.closest("tr").remove();

        }

    }

});

const search=document.getElementById("searchInput");

search.addEventListener("keyup",()=>{

    const value=search.value.toLowerCase();

    document.querySelectorAll("tbody tr").forEach(row=>{

        row.style.display=row.innerText.toLowerCase().includes(value) ? "" : "none";

    });

});
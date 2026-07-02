const modal=document.querySelector(".modal");

const openBtn=document.getElementById("openModal");

const closeBtn=document.querySelector(".close");

const cancelBtn=document.querySelector(".btn-cancel");

const modalTitle=document.getElementById("modalTitle");

const saveBtn=document.getElementById("saveBtn");

const numero=document.getElementById("numero");

const tipo=document.getElementById("tipo");

const capacidade=document.getElementById("capacidade");

const valor=document.getElementById("valor");

function abrirModal(){

    modal.classList.add("active");

}

function fecharModal(){

    modal.classList.remove("active");

}

function limpar(){

    numero.value="";

    tipo.selectedIndex=0;

    capacidade.value="";

    valor.value="";

}

function somenteLeitura(v){

    numero.readOnly=v;

    tipo.disabled=v;

    capacidade.readOnly=v;

    valor.readOnly=v;

}

openBtn.onclick=()=>{

    modalTitle.innerText="Novo Quarto";

    limpar();

    somenteLeitura(false);

    saveBtn.style.display="inline-block";

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

        const d=this.closest("tr").querySelectorAll("td");

        modalTitle.innerText="Visualizar Quarto";

        numero.value=d[0].innerText;

        tipo.value=d[1].innerText;

        capacidade.value=d[2].innerText.replace(" Pessoa","").replace(" Pessoas","");

        valor.value=d[3].innerText.replace("R$ ","").replace(",00","");

        somenteLeitura(true);

        saveBtn.style.display="none";

        abrirModal();

    }

});

document.querySelectorAll(".edit").forEach(btn=>{

    btn.onclick=function(){

        const d=this.closest("tr").querySelectorAll("td");

        modalTitle.innerText="Editar Quarto";

        numero.value=d[0].innerText;

        tipo.value=d[1].innerText;

        capacidade.value=d[2].innerText.replace(" Pessoa","").replace(" Pessoas","");

        valor.value=d[3].innerText.replace("R$ ","").replace(",00","");

        somenteLeitura(false);

        saveBtn.style.display="inline-block";

        abrirModal();

    }

});

document.querySelectorAll(".delete").forEach(btn=>{

    btn.onclick=function(){

        if(confirm("Deseja excluir este quarto?")){

            this.closest("tr").remove();

        }

    }

});
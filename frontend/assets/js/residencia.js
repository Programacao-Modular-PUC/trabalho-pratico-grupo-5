const modal = document.querySelector(".modal");

const openBtn = document.getElementById("openModal");
const closeBtn = document.querySelector(".close");
const cancelBtn = document.querySelector(".btn-cancel");

const modalTitle = document.getElementById("modalTitle");
const saveBtn = document.getElementById("saveBtn");

const nome = document.getElementById("nomeResidencia");
const endereco = document.getElementById("endereco");
const cidade = document.getElementById("cidade");
const capacidade = document.getElementById("capacidade");

function abrirModal() {
    modal.classList.add("active");
}

function fecharModal() {
    modal.classList.remove("active");
}

function limparFormulario() {
    nome.value = "";
    endereco.value = "";
    cidade.value = "";
    capacidade.value = "";
}

function somenteLeitura(valor) {
    nome.readOnly = valor;
    endereco.readOnly = valor;
    cidade.readOnly = valor;
    capacidade.readOnly = valor;
}

openBtn.onclick = () => {

    modalTitle.innerText = "Nova Residência";

    limparFormulario();

    somenteLeitura(false);

    saveBtn.style.display = "inline-block";

    abrirModal();

};

closeBtn.onclick = fecharModal;

cancelBtn.onclick = fecharModal;

window.onclick = (e) => {

    if (e.target === modal) {

        fecharModal();

    }

};

document.querySelectorAll(".view").forEach(btn => {

    btn.onclick = function () {

        const dados = this.closest("tr").querySelectorAll("td");

        modalTitle.innerText = "Visualizar Residência";

        nome.value = dados[0].innerText;
        endereco.value = dados[1].innerText;
        cidade.value = dados[2].innerText;
        capacidade.value = dados[3].innerText.replace(" Quartos", "");

        somenteLeitura(true);

        saveBtn.style.display = "none";

        abrirModal();

    };

});

document.querySelectorAll(".edit").forEach(btn => {

    btn.onclick = function () {

        const dados = this.closest("tr").querySelectorAll("td");

        modalTitle.innerText = "Editar Residência";

        nome.value = dados[0].innerText;
        endereco.value = dados[1].innerText;
        cidade.value = dados[2].innerText;
        capacidade.value = dados[3].innerText.replace(" Quartos", "");

        somenteLeitura(false);

        saveBtn.style.display = "inline-block";

        abrirModal();

    };

});

document.querySelectorAll(".delete").forEach(btn => {

    btn.onclick = function () {

        if (confirm("Deseja excluir esta residência?")) {

            this.closest("tr").remove();

        }

    };

});

const search = document.getElementById("searchInput");

search.addEventListener("keyup", () => {

    const valor = search.value.toLowerCase();

    document.querySelectorAll("tbody tr").forEach(linha => {

        linha.style.display = linha.innerText.toLowerCase().includes(valor)
            ? ""
            : "none";

    });

});
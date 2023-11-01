//Script controlador do menu superior do site

function hideall(){
    document.getElementById("iniciocaixa_div").style.display = "none"; 
    document.getElementById("consultar_form").style.display = "none";     
}

function iniciofired(){
    hideall();
    document.getElementById("iniciocaixa_div").style.display = "block";   
    console.log("teste");
}
function consultarfired(){
    hideall();
    document.getElementById("consultar_form").style.display = "block";
    console.log("consultar");
}
function sobrefired(){
    console.log("teste");
}
function eventosfired(){
    console.log("teste");
}


document.getElementById("inicio_button").addEventListener("click",iniciofired);
document.getElementById("consultar_button").addEventListener("click",consultarfired);
document.getElementById("sobre_button").addEventListener("click",sobrefired);
document.getElementById("eventos_button").addEventListener("click",eventosfired);







//Ajax
var bookjson;
var quantidade_livros_carregados = 0;

async function requestBook(){
   const nome =  document.getElementById("bookname").value
   console.log(nome)
   nome.replace(" ", "+")
   document.getElementById("consultar_form").style.display = "none"
   console.log("URL = busca-acervo?nomedolivro=" + nome)
   const promisedbinfo = await fetch("busca-acervo?nomedolivro=" + nome)
   bookjson = await promisedbinfo.json()
   bookjson.livros.forEach(addBooks)
}


function addBooks(bookjson){
    const div = document.getElementById("bookdiv").cloneNode(true)
    const nomeparagrafo = document.createElement("p")   
    const sinopseparagrafo = document.createElement("p")
    document.getElementById("resultados").style.display = "block";
    nomeparagrafo.style.display = "inline-block";
    nomeparagrafo.style.position = "absolute";    
    nomeparagrafo.style.color = "white"
    sinopseparagrafo.style.display = "inline-block";
    sinopseparagrafo.style.position = "absolute";        
    sinopseparagrafo.style.color = "white"
    sinopseparagrafo.style.top = "30px";
    sinopseparagrafo.style.width = "350px";    
    nomeparagrafo.innerHTML = bookjson.nome
    sinopseparagrafo.innerHTML = bookjson.sinopse
    div.appendChild(nomeparagrafo)
    div.appendChild(sinopseparagrafo)
    div.style.display = "inline-block"
    div.querySelector("#bookimage").setAttribute("src","/findcover?isbn=" + bookjson.isbn)
    div.querySelector("#bookimage").style.display = "inline-block";
    document.body.appendChild(div)
    console.log(bookjson.nome)
    console.log(bookjson.sinopse)
} 
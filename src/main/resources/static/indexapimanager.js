//Ajax
var bookjson;


async function requestBook(){
   const nome =  document.getElementById("bookname").value
   console.log(nome)
   nome.replace(" ", "+")
   document.getElementById("consultar_form").style.display = "none"
   console.log("URL = busca-acervo?nomedolivro=" + nome)
   const promise = await fetch("busca-acervo?nomedolivro=" + nome)
   bookjson = await promise.json()
   addBooks(bookjson)
}


function addBooks(bookjson){
    const div = document.getElementById("bookdiv").cloneNode()
    const nomeparagrafo = document.createElement("p")
    const sinopseparagrafo = document.createElement("p")
    nomeparagrafo.style.color = "white"
    sinopseparagrafo.style.color = "white"
    nomeparagrafo.innerHTML = bookjson.nome
    sinopseparagrafo.innerHTML = bookjson.sinopse
    div.appendChild(nomeparagrafo)
    div.appendChild(sinopseparagrafo)
    div.style.display = "block"
    document.body.appendChild(div)
    console.log(bookjson.nome)
    console.log(bookjson.sinopse)
} 
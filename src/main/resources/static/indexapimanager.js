//Made by Kkraft


//Ajax
var bookjson;
var quantidade_livros_carregados = 0;

var firstexecution = true
var finalstring = ""
var spacebefore = false
function formatString(char){

    if(firstexecution){
        finalstring += char.toUpperCase()
        firstexecution = false
        return "continue dont work on function .-"
    }
    
    if(spacebefore){
        finalstring += char.toUpperCase()
        spacebefore = false
        return "continue dont work on function .-"
    }
    
    if(char == " "){
        spacebefore = true
        finalstring += char
        return "continue dont work on function .-"
    }
    
    finalstring += char // Se nenhum if disparar
}

async function requestBook(){
   var nome =  document.getElementById("bookname").value
   console.log(nome)
   const nomesplitted = Array.from(nome);
   arraylength = nomesplitted.length
   nomesplitted.forEach(formatString)
   nome = finalstring
   console.log(nome)
   document.getElementById("consultar_form").style.display = "none"
   console.log("URL = busca-acervo?nomedolivro=" + nome)
   const promisedbinfo = await fetch("busca-acervo?nomedolivro=" + nome)
   bookjson = await promisedbinfo.json()
   bookjson.livros_e_autores.forEach(addBooks)
}


function addBooks(bookjson){
    if(bookjson.livro_ou_autor === "livro"){
        const div = document.getElementById("bookdiv").cloneNode(true)
        const nomeparagrafo = document.createElement("p")   
        const sinopseparagrafo = document.createElement("p")
        const autorlink = document.createElement("a")    
        document.getElementById("resultados").style.display = "block"
        nomeparagrafo.style.display = "inline-block"
        nomeparagrafo.style.position = "absolute"   
        nomeparagrafo.style.color = "white"
        sinopseparagrafo.style.display = "inline-block"
        sinopseparagrafo.style.position = "absolute"      
        sinopseparagrafo.style.color = "white"
        sinopseparagrafo.style.top = "30px";
        sinopseparagrafo.style.width = "350px";   
        autorlink.style.position = "relative"
        autorlink.style.bottom = "4px"
        nomeparagrafo.innerHTML = bookjson.nome
        sinopseparagrafo.innerHTML = bookjson.sinopse 
        autorlink.innerHTML = bookjson.autor
        autorlink.setAttribute("href","/autor?autor=" + bookjson.autor)
        div.appendChild(nomeparagrafo)
        div.appendChild(sinopseparagrafo)
        div.appendChild(autorlink)
        div.style.display = "inline-block"
        div.querySelector("#bookimage").setAttribute("src","/findcover?isbn=" + bookjson.isbn)
        div.querySelector("#bookimage").style.display = "inline-block";
        document.body.appendChild(div)
        console.log(bookjson.nome)
        console.log(bookjson.sinopse)
    }else{
        const div = document.getElementById("autordiv").cloneNode(true)  
        div.style.display = "inline-block"        
        document.getElementById("resultados").style.display = "block"        
        const autorlink = document.createElement("a")
        const autorparagrafo = document.createElement("p")
        autorlink.innerHTML = bookjson.nome
        autorlink.setAttribute("href","/autor?autor="+ bookjson.id )
        autorlink.style.position = "relative"
        autorlink.style.top = "10px"
        autorlink.style["font-size"] = "xx-large"
        autorparagrafo.style["font-size"] = "x-large"
        autorparagrafo.innerHTML = "Autor"
        div.appendChild(autorlink)
        div.appendChild(autorparagrafo)
        document.body.appendChild(div)
    }  
} 
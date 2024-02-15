//Adapted by Kkraft(from my own script .-)


var bookjson;


bookjson.livros.forEach(addBooks)


function addBooks(bookjson){
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
} 
//Made by Mateus Rocha(Kkrafy)


//Ajax
var bookjson;
var quantidade_livros_carregados = 0;

var firstexecution = true
var finalstring = ""
var spacebefore = false

function setInnerHtml(content,element){
    var nomeargs = content.split("||bold||")
      if(nomeargs.length > 1){
        if(nomeargs[0] != ""){
           element.innerHTML = nomeargs[0]
        } 
        var open_bold = true;
        nomeargs.forEach( (n,i) =>{ //||bold||teste||bold|| batata ||bold||teste2||bold||
            if(i != 0 & open_bold){
                console.log(n+" openbold true")
                element.innerHTML += "<strong>" + n + "</strong>"
                open_bold = false
            }else if(!open_bold){
                console.log(n+" openbold false")                
                element.innerHTML += n
                open_bold = true
            }
         }) 
      }else{
          console.log("nenhum bold")
          element.innerHTML += content
        }    
}

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
   var prompt = document.getElementById("bookname").value
   console.log(prompt)
   const nomesplitted = Array.from(prompt);
   arraylength = nomesplitted.length
   nomesplitted.forEach(formatString)
   prompt = finalstring
   console.log(finalstring)
   console.log("URL = busca-acervo?prompt=" + prompt)
   const promisedbinfo = await fetch("busca-acervo?prompt=" + prompt)
   bookjson = await promisedbinfo.json()
   if(bookjson.isPresent){
       bookjson.livros_e_autores.forEach(addBooks)
       document.getElementById("consultar_form").style.display = "none"       
   }else{
       prompt = "";
       finalstring = "";
       spacebefore = false
       firstexecution = true
       alert("Nenhum livro encontrado ☹️ , verifique se voce escreveu corretamente.")
   }
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
        sinopseparagrafo.style.top = "30px"
        sinopseparagrafo.style.width = "350px"
        autorlink.style.position = "relative"
        autorlink.style.bottom = "4px"
        setInnerHtml(bookjson.nome,nomeparagrafo)
        setInnerHtml(bookjson.sinopse,sinopseparagrafo)
        setInnerHtml(bookjson.autor,autorlink)
        autorlink.setAttribute("href","/autor?autor=" + bookjson.autorid)
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
        const autornome = document.createElement("p")
        const autorlink = document.createElement("a")
        const autorparagrafo = document.createElement("p")
        setInnerHtml(bookjson.nome,autornome)
        autorlink.innerHTML = "Pagina do autor"
        autorlink.setAttribute("href","/autor?autor="+ bookjson.id )
        autornome.style.position = "relative"
        autorparagrafo.style.position = "relative"
        autorlink.style.position = "relative"        
        autorlink.style.top = "17%"
        autorparagrafo.style.bottom = "12%"
        autornome.style.bottom = "7%"        
        autornome.style["font-size"] = "xx-large"
        autorlink.style["font-size"] = "x-large"        
        autorparagrafo.style["font-size"] = "x-large"
        autorparagrafo.innerHTML = "Autor"
        div.appendChild(autornome)
        div.appendChild(autorparagrafo)        
        div.appendChild(autorlink)
        document.body.appendChild(div)
    }  
} 
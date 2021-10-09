const QUESTION = "Desa eliminar este usuario?"
// Call the dataTables jQuery plugin
$(document).ready(function() {
    loadDataUsers()
  $('#dataTableUsers').DataTable();
});

function getHeaders(){
        return {
         'Accept': 'application/json',
         'Content-Type': 'application/json',
         'Authorization': localStorage.token //se agrega el token que se consiguio despues de iniciar sesion
       }
}

async function loadDataUsers(){
   const request = await fetch('api/users', {
       method: 'GET',
       headers: getHeaders()
     });
     const usersList = await request.json();
     console.log(usersList);

     let usuario = '';
     for(let user of usersList){
     let bntHtmlDelete = '<a href="#" onclick="deleteUser('+ user.id +')" class="btn btn-danger btn-circle"><i class="fas fa-trash"></i></a>'
     let phone = user.phone == null ? '-' : user.phone;
     usuario +=
        '<tr><td>'+user.id+</td><td>'+user.name+' '+user.lastName+'</td>'+
        '<td>Titan</td><td>'+user.email+'</td>'+
        '<td>'+phone+'</td>'+
        '<td>'+bntHtmlDelete+'</td></tr>';
     }

    document.querySelector('#dataTableUsers tbody').outerHTML = usuario;
}

async function deleteUser(id){

    if(!confirm(QUESTION)){
        return; //para el metodo!
    }
    const request = await fetch('api/users/'+id, {
       method: 'DELETE', //desde aqui mandamos a que mtodo debe de hacer referencia de nuestro controller
       headers:getHeaders()
    });
    location.reload(); //recarga la pagina
}

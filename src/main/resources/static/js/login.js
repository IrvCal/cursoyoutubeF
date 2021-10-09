async function login(){

   let data = {};

   data.name = document.getElementById('ti_name').value;
   data.password = document.getElementById('ti_password').value;

   const request = await fetch('api/login', {
       method: 'POST',
       headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
       },
     body: JSON.stringify(data)
     });
     const response = await request.text();

    if(response != 'FAIL'){
        localStorage.token = response;
        localStorage.email = datos.email; // hace referencia a la variable inicial que dio el usuario
        window.location.href = "users.html";
    }else{
        alert('Las credenciales son incorrectas')
    }
}



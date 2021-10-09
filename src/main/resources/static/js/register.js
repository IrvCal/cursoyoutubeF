async function registerUser(){

   let data = {};

   data.name = document.getElementById('ti_name').value;
   data.lastname = document.getElementById('ti_lastname').value;
   data.email = document.getElementById('ti_email').value;
   data.password = document.getElementById('ti_password').value;
// hacer en db el campo phone como null
   let confirmPassword = document.getElementById('ti_password_confirm').value;
    if(confirmPassword !=  data.password){
        alert('Constrasenias no coinciden');
        return;
    }
   const request = await fetch('api/users', {
       method: 'POST',
       headers: {
         'Accept': 'application/json',
         'Content-Type': 'application/json'
       },
     body: JSON.stringify(data)
     });
}



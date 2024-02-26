document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('loginForm');
    
    form.addEventListener('submit', function(e) {
        e.preventDefault(); // Prevent default form submission

        // Collect user input from the form
        const loginData = {
            user_name: document.getElementById('username').value,
            password: document.getElementById('password').value,
        };

        // Send the user data to the server via POST request
        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(loginData),
        })
        .then(response => {
            if (!response.ok) {
                // If the server response is not ok, throw an error
                throw new Error('Login request failed with status: ' + response.status);
            }
            return response.json(); // Parse the JSON of the response
        })
        .then(data => {
            if (data.success === true) {
                // If the response contains success: "true", handle successful login
                alert('Login successful!');
                window.location.href = '/frontend/dashboard.html'; // Redirect user to the dashboard or another page
            } else {
                // If the success property does not equal "true", show an error message
                throw new Error(data.message || 'Login failed. Please try again.');
            }
        })
        .catch(error => {
            console.error('Error during login:', error);
            alert(error.message); // Display the error message to the user
        });
    });
});
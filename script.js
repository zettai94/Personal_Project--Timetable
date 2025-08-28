const wrapper = document.querySelector('.wrapper');
const loginLink = document.querySelector('.login-link');
const registerLink = document.querySelector('.register-link');
const btnPopup = document.querySelector('.loginButt-popup');
const iconClose = document.querySelector('.icon-close');   

//toggle panels
registerLink.addEventListener('click', () => {
    wrapper.classList.add('active');
});

loginLink.addEventListener('click', () => {
    wrapper.classList.remove('active');
});

btnPopup.addEventListener('click', () => {
    wrapper.classList.add('active-popup');
});

iconClose.addEventListener('click', () => {
    wrapper.classList.remove('active-popup');
});


//login form
const loginForm = document.querySelector('.form-box.login form');
const loginUrl = "http://localhost:8080/api/auth/login";

loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    try {
        const response = await fetch(loginUrl, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password }),
        });

        if (response.ok) {
            alert("Login successful!");
        }
        else if(response.status === 401)
        {
            alert("Unauthorized");
        }
        else
        {
           alert("Some error occurred. Please try again later. Status code: " + response.status);
        }
    }
    catch (error)
    {
        console.error("Error during login:", error);
        alert("An error occurred. Please try again later. AGAIN :(");
    }
});
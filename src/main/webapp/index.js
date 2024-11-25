// Assuming image paths are properly set in the 'food_list' array



// Call the function to populate food items when the page loads
document.addEventListener("DOMContentLoaded", populateFoodItems);



function showLoginPopup() {
    document.getElementById('loginPopup').style.display = 'flex';
}


function showLoginPopup() {
    document.getElementById('loginPopup').style.display = 'flex';
}


function closeLoginPopup() {
    document.getElementById('loginPopup').style.display = 'none';
}

function toggleFormState() {
    const formTitle = document.querySelector('.login-popup-title h2');
    const nameInput = document.getElementById('nameInput');
    const submitButton = document.getElementById('submitButton');
    const toggleText = document.querySelector('.login-popup-container p');

    if (formTitle.textContent === 'Login') {
        formTitle.textContent = 'Sign Up';
        nameInput.style.display = 'block'; 
        submitButton.textContent = 'Create Account';
        toggleText.innerHTML = 'Already have an account? <span onclick="toggleFormState()">Login Here</span>';
    } else {
        formTitle.textContent = 'Login';
        nameInput.style.display = 'none'; // Hide the Name input field
        submitButton.textContent = 'Login';
        toggleText.innerHTML = 'Don\'t have an account? <span onclick="toggleFormState()">Click Here</span>';
    }
}

// Function to validate form inputs
// Function to validate form inputs
function validateForm() {
    const formTitle = document.querySelector('.login-popup-title h2').textContent;
    const nameInput = document.getElementById('nameInput');
    const emailInput = document.getElementById('emailInput');
    const passwordInput = document.getElementById('passwordInput');
    
    // Check for empty email and password
    if (emailInput.value.trim() === '' || passwordInput.value.trim() === '') {
        alert('Please fill in all required fields.');
        return false;
    }

    // Check for password length
    if (passwordInput.value.length < 8) {
        alert('Password must be at least 8 characters long.');
        return false;
    }

    // For Sign Up, also check the name input field
    if (formTitle === 'Sign Up' && nameInput.value.trim() === '') {
        alert('Please enter your name.');
        return false;
    }

    // If all validations pass
    return true;
}

// Add the validation to the form submit button
document.getElementById('submitButton').addEventListener('click', function(event) {
    event.preventDefault(); // Prevent default form submission if validation fails
    
    if (validateForm()) {
        // Find the form and submit it manually after validation
        document.querySelector('.login-popup-container').submit();
    }
});

function addToCart(id) {
    // Handle logic for adding an item to the cart here
    document.getElementById('add-btn').style.display = 'none'; // Hide 'Add' button
    document.getElementById('food-item-counter').style.display = 'flex'; // Show the counter block
    // Update cart count logic here
}

function removeFromCart(id) {
    // Handle logic for removing an item from the cart here
    let cartCount = document.getElementById('cart-count');
    if (parseInt(cartCount.textContent) > 1) {
        cartCount.textContent = parseInt(cartCount.textContent) - 1; // Decrease the count
    } else {
        document.getElementById('add-btn').style.display = 'inline'; // Show 'Add' button again
        document.getElementById('food-item-counter').style.display = 'none'; // Hide the counter block
    }
}
/**
 * 
 */
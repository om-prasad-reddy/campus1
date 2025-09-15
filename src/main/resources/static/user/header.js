document.addEventListener('DOMContentLoaded', function() {
    // Get current page path
    const currentPath = window.location.pathname;
    const filename = currentPath.split('/').pop();
    
    // Hide signup link on register page
    if (filename === 'register.html') {
        const signupLinks = document.querySelectorAll('#desktop-signup, #mobile-signup');
        signupLinks.forEach(link => {
            if (link) link.style.display = 'none';
        });
    }
    
    // Hide login link on login page
    if (filename === 'login.html') {
        const loginLinks = document.querySelectorAll('#desktop-login, #mobile-login');
        loginLinks.forEach(link => {
            if (link) link.style.display = 'none';
        });
        
        // Prevent back navigation on login page
        preventBackNavigation();
    }
    
    // Check for logout flag on login page
    if (filename === 'login.html') {
        const logoutFlag = sessionStorage.getItem('logoutFlag') === 'true' || 
                           localStorage.getItem('logoutFlag') === 'true' ||
                           window.location.search.includes('logout=true');
        
        if (logoutFlag) {
            // Clear all storage completely
            clearAllStorage();
            
            // Don't attempt automatic login
            console.log('Logout detected, skipping automatic login');
        }
    }
    
    // Initialize Lucide icons if available
    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }
    
    // Initialize header
    updateHeader();
    
    // Check authentication on protected pages
    checkAuthentication();
});

// Prevent back navigation function
function preventBackNavigation() {
    window.history.pushState(null, null, window.location.href);
    window.onpopstate = function() {
        window.history.pushState(null, null, window.location.href);
        // Force redirect to login page if user tries to go back
        if (window.location.pathname !== '/login.html') {
            window.location.replace('login.html?logout=true');
        }
    };
}

// Clear all storage completely
function clearAllStorage() {
    // Clear sessionStorage
    sessionStorage.clear();
    
    // Clear localStorage
    localStorage.clear();
    
    // Clear all cookies
    document.cookie.split(";").forEach(function(c) { 
        document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/"); 
    });
    
    // Clear cache (if possible)
    if ('caches' in window) {
        caches.keys().then(names => {
            names.forEach(name => {
                caches.delete(name);
            });
        });
    }
}

// Check authentication on protected pages
function checkAuthentication() {
    const currentPath = window.location.pathname;
    const filename = currentPath.split('/').pop();
    
    // Skip check on login and register pages
    if (filename === 'login.html' || filename === 'register.html') {
        return;
    }
    
    // Check if user is logged in
    let loggedInUser = sessionStorage.getItem("loggedInUser");
    let userData = null;
    let isLoggedIn = false;
    
    // Check sessionStorage
    if (loggedInUser) {
        userData = JSON.parse(loggedInUser);
        isLoggedIn = true;
    } else {
        // Check localStorage
        const persistent = localStorage.getItem("persistentLogin");
        if (persistent) {
            const persistentData = JSON.parse(persistent);
            const expiresAt = new Date(persistentData.expiresAt);
            if (expiresAt > new Date()) {
                userData = persistentData;
                isLoggedIn = true;
            } else {
                // Expired: clear persistent data
                localStorage.removeItem("persistentLogin");
            }
        }
    }
    
    // If not logged in, redirect to login page
    if (!isLoggedIn) {
        const timestamp = new Date().getTime();
        window.location.replace(`login.html?t=${timestamp}&logout=true`);
    }
}

// Update header function
window.updateHeader = function() {
    // Check for logout flag first
    const logoutFlag = sessionStorage.getItem('logoutFlag') === 'true' || 
                       localStorage.getItem('logoutFlag') === 'true' ||
                       window.location.search.includes('logout=true');
    
    if (logoutFlag) {
        // Clear all storage
        clearAllStorage();
        
        // Force logged-out state
        const desktopLogin = document.getElementById("desktop-login");
        const desktopSignup = document.getElementById("desktop-signup");
        const desktopSignout = document.getElementById("desktop-signout");
        const mobileLogin = document.getElementById("mobile-login");
        const mobileSignup = document.getElementById("mobile-signup");
        const mobileSignout = document.getElementById("mobile-signout");
        const desktopUsername = document.getElementById("desktop-username");
        const mobileUsername = document.getElementById("mobile-username");
        
        // Show logged-out UI
        if (desktopLogin) desktopLogin.classList.remove("hidden");
        if (desktopSignup) desktopSignup.classList.remove("hidden");
        if (mobileLogin) mobileLogin.classList.remove("hidden");
        if (mobileSignup) mobileSignup.classList.remove("hidden");
        if (desktopSignout) desktopSignout.classList.add("hidden");
        if (mobileSignout) mobileSignout.classList.add("hidden");
        if (desktopUsername) desktopUsername.classList.add("hidden");
        if (mobileUsername) mobileUsername.classList.add("hidden");
        
        return;
    }
    
    let loggedInUser = sessionStorage.getItem("loggedInUser");
    let userData = null;
    let isLoggedIn = false;
    
    // Check sessionStorage
    if (loggedInUser) {
        userData = JSON.parse(loggedInUser);
        isLoggedIn = true;
    } else {
        // Check localStorage
        const persistent = localStorage.getItem("persistentLogin");
        if (persistent) {
            const persistentData = JSON.parse(persistent);
            const expiresAt = new Date(persistentData.expiresAt);
            if (expiresAt > new Date()) {
                userData = persistentData;
                isLoggedIn = true;
            } else {
                // Expired: clear persistent data
                localStorage.removeItem("persistentLogin");
            }
        }
    }
    
    const desktopLogin = document.getElementById("desktop-login");
    const desktopSignup = document.getElementById("desktop-signup");
    const desktopSignout = document.getElementById("desktop-signout");
    const mobileLogin = document.getElementById("mobile-login");
    const mobileSignup = document.getElementById("mobile-signup");
    const mobileSignout = document.getElementById("mobile-signout");
    const desktopUsername = document.getElementById("desktop-username");
    const mobileUsername = document.getElementById("mobile-username");
    
    if (isLoggedIn && userData) {
        // Show logged-in UI
        if (desktopLogin) desktopLogin.classList.add("hidden");
        if (desktopSignup) desktopSignup.classList.add("hidden");
        if (mobileLogin) mobileLogin.classList.add("hidden");
        if (mobileSignup) mobileSignup.classList.add("hidden");
        if (desktopSignout) desktopSignout.classList.remove("hidden");
        if (mobileSignout) mobileSignout.classList.remove("hidden");
        if (desktopUsername) {
            desktopUsername.classList.remove("hidden");
            desktopUsername.textContent = `Hi, ${userData.firstName}`;
        }
        if (mobileUsername) {
            mobileUsername.classList.remove("hidden");
            mobileUsername.textContent = `Hi, ${userData.firstName}`;
        }
    } else {
        // Show logged-out UI
        if (desktopLogin) desktopLogin.classList.remove("hidden");
        if (desktopSignup) desktopSignup.classList.remove("hidden");
        if (mobileLogin) mobileLogin.classList.remove("hidden");
        if (mobileSignup) mobileSignup.classList.remove("hidden");
        if (desktopSignout) desktopSignout.classList.add("hidden");
        if (mobileSignout) mobileSignout.classList.add("hidden");
        if (desktopUsername) desktopUsername.classList.add("hidden");
        if (mobileUsername) mobileUsername.classList.add("hidden");
    }
    
    // Re-render icons if Lucide is available
    if (typeof lucide !== 'undefined') {
        lucide.createIcons();
    }
};

// Enhanced logout function
function performLogout() {
    // Clear all storage completely
    clearAllStorage();
    
    // Set logout flags to prevent automatic login
    sessionStorage.setItem('logoutFlag', 'true');
    localStorage.setItem('logoutFlag', 'true');
    
    // Update header
    if (typeof window.updateHeader === 'function') {
        window.updateHeader();
    }
    
    console.log('Logout successful');
    
    // Redirect to login page with cache-busting and logout flag
    const timestamp = new Date().getTime();
    window.location.replace(`login.html?t=${timestamp}&logout=true`);
}

// Sign-out functionality
function signOut() {
    performLogout();
}

// Logo click functionality
function refreshPage() {
    window.location.reload();
}

// Mobile menu toggle
document.addEventListener('DOMContentLoaded', function() {
    const mobileMenuBtn = document.getElementById("mobile-menu-btn");
    const mobileNav = document.getElementById("mobile-nav");
    
    if (mobileMenuBtn && mobileNav) {
        mobileMenuBtn.addEventListener("click", () => {
            mobileNav.classList.toggle("hidden");
            const isOpen = !mobileNav.classList.contains("hidden");
            
            // Update menu icon
            if (typeof lucide !== 'undefined') {
                mobileMenuBtn.innerHTML = `<i data-lucide="${isOpen ? 'x' : 'menu'}" class="h-6 w-6"></i>`;
                lucide.createIcons();
            } else {
                // Fallback if Lucide isn't available
                mobileMenuBtn.innerHTML = isOpen ? 
                    '<svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path></svg>' :
                    '<svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path></svg>';
            }
        });
    }
    
    // Attach event listeners for signout buttons
    const desktopSignout = document.getElementById("desktop-signout");
    const mobileSignout = document.getElementById("mobile-signout");
    const logoContainer = document.getElementById("logo-container");
    
    if (desktopSignout) {
        desktopSignout.addEventListener("click", signOut);
    }
    
    if (mobileSignout) {
        mobileSignout.addEventListener("click", signOut);
    }
    
    if (logoContainer) {
        logoContainer.addEventListener("click", refreshPage);
    }
});
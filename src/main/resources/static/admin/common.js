



// Common header and navigation functionality
function loadHeaderAndNav() {
    // Get admin data from localStorage
    const admin = JSON.parse(localStorage.getItem('admin')) || {};
    
    // Create header HTML
    const headerHTML = `
        <div class="header">
            <div class="logo">
                <i class="fas fa-graduation-cap"></i>
                <span>Campus1</span>
            </div>
            <div class="header-right">
                <div class="date-time">
                    <i class="fas fa-check-circle"></i>
                    <span id="current-datetime"></span>
                </div>
                <div class="profile-section">
                    <div class="admin-info">
                        <div class="admin-name">${admin.fullName || 'Admin User'}</div>
                        <div class="admin-email">${admin.email || 'admin@campus1.com'}</div>
                    </div>
                    <div class="profile-avatar" onclick="triggerPhotoUpload()">
                        <img src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIHZpZXdCb3g9IjAgMCA0MCA0MCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPGNpcmNsZSBjeD0iMjAiIGN5PSIyMCIgcj0iMjAiIGZpbGw9IiM2Yjc0ZGIiLz4KPHN2ZyB4PSI4IiB5PSI4IiB3aWR0aD0iMjQiIGhlaWdodD0iMjQiIHZpZXdCb3g9IjAgMCAyNCAyNCIgZmlsbD0ibm9uZSI+CjxwYXRoIGQ9Ik0xMiAxMkM5Ljc5IDEyIDggMTAuMjEgOCA4UzkuNzkgNCA1IDRTMTYgNS43OSAxNiA4UzE0LjIxIDEyIDEyIDEyWk0xMiAxNEM5LjMzIDE0IDQgMTUuMzQgNCAyMFYyMkgyMFYyMEMxNiAxNS4zNCAxNC42NyAxNCAxMiAxNFoiIGZpbGw9IndoaXRlIi8+Cjwvc3ZnPgo8L3N2Zz4K" alt="Profile" id="header-profile-img">
                        <div class="upload-overlay">
                            <i class="fas fa-camera"></i>
                        </div>
                    </div>
                    <input type="file" id="profile-photo-input" accept="image/jpeg,image/png,image/jpg" style="display: none;" onchange="handlePhotoUpload(event)">
                </div>
            </div>
        </div>
    `;
    
    // Create sidebar HTML
    const sidebarHTML = `
        <div class="sidebar">
            <h2>Admin Dashboard</h2>
            <a href="admin/admin_profile.html"><i class="fas fa-user-shield"></i> Admin Profile</a>
            <a href="admin/resume_users_data.html"><i class="fas fa-file-alt"></i> TASK</a>
            <a href="#" onclick="logout()"><i class="fas fa-sign-out-alt"></i> Logout</a>
        </div>
    `;
    
    // Insert header at the beginning of body
    document.body.insertAdjacentHTML('afterbegin', headerHTML);
    
    // Insert sidebar after header
    const header = document.querySelector('.header');
    header.insertAdjacentHTML('afterend', sidebarHTML);
    
    // Update date and time
    updateDateTime();
    setInterval(updateDateTime, 60000);
}

// Function to refresh header data
function refreshHeader() {
    const admin = JSON.parse(localStorage.getItem('admin')) || {};
    
    // Update header name and email
    const headerName = document.querySelector('.admin-name');
    const headerEmail = document.querySelector('.admin-email');
    
    if (headerName) headerName.textContent = admin.fullName || 'Admin User';
    if (headerEmail) headerEmail.textContent = admin.email || 'admin@campus1.com';
}

// Function to update date and time
function updateDateTime() {
    const now = new Date();
    const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit',
        hour12: true,
        timeZone: 'Asia/Kolkata'
    };
    const dateTimeString = now.toLocaleDateString('en-US', options) + ' IST';
    const dateTimeElement = document.getElementById('current-datetime');
    if (dateTimeElement) {
        dateTimeElement.textContent = dateTimeString;
    }
}

// Function to trigger photo upload
function triggerPhotoUpload() {
    document.getElementById('profile-photo-input').click();
}

// Function to handle photo upload
function handlePhotoUpload(event) {
    const file = event.target.files[0];
    if (file) {
        // Check file size (2MB limit)
        if (file.size > 2 * 1024 * 1024) {
            alert('File size should not exceed 2MB');
            return;
        }
        
        // Check file type
        if (!file.type.match(/^image\/(jpeg|jpg|png)$/)) {
            alert('Please select a valid image file (JPG, PNG)');
            return;
        }
        
        const reader = new FileReader();
        reader.onload = function(e) {
            const imageUrl = e.target.result;
            
            // Update header profile image
            const headerImg = document.getElementById('header-profile-img');
            if (headerImg) {
                headerImg.src = imageUrl;
            }
            
            // Update large profile image if it exists
            const largeProfileImg = document.getElementById('profile-image-large');
            if (largeProfileImg) {
                largeProfileImg.src = imageUrl;
            }
            
            // Save to localStorage
            localStorage.setItem('profilePhoto', imageUrl);
        };
        reader.readAsDataURL(file);
    }
}

// Function to load saved profile photo
function loadSavedProfilePhoto() {
    const savedPhoto = localStorage.getItem('profilePhoto');
    if (savedPhoto) {
        const headerImg = document.getElementById('header-profile-img');
        if (headerImg) {
            headerImg.src = savedPhoto;
        }
        
        const largeProfileImg = document.getElementById('profile-image-large');
        if (largeProfileImg) {
            largeProfileImg.src = savedPhoto;
        }
    }
}

// Logout function
function logout() {
    localStorage.removeItem('admin');
    localStorage.removeItem('isLoggedIn');
    window.location.href = '/login.html';
}

// Load header and navigation when DOM is ready
document.addEventListener('DOMContentLoaded', function() {
    // Check if user is logged in
    if (localStorage.getItem('isLoggedIn') !== 'true') {
        window.location.href = '/login.html';
        return;
    }
    
    // Load header and navigation
    loadHeaderAndNav();
    
    // Load saved profile photo after a short delay to ensure elements are loaded
    setTimeout(loadSavedProfilePhoto, 100);
});
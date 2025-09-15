// API Base URL
const API_BASE_URL = 'http://localhost:8081';
// State management
let currentTabIndex = 0;
const tabs = [
    'personal', 'education', 'skills', 'experience', 'projects',
    'certifications', 'achievements', 'languages', 'hobbies', 'references'
];
let formData = {};
let currentUser = null;
let userId = null;
// File storage
let profilePhotoFile = null;
let signatureFile = null;
let certificateFiles = {};
// Comprehensive list of languages/mother tongues
const motherTongues = [
    "English", "Assamese", "Bengali", "Chakma", "Haijong/Hajong", "Rajbangsi", 
    "Bodo/Boro", "Kachari", "Mech/Mechhia", "Dogri", "Gujarati", "Gujrao/Gujrau", 
    "Pattani", "Ponchi", "Saurashtra/Saurashtri", "Awadhi", "Baghati/Baghati Pahari", 
    "Bagheli/Baghel Khandi", "Bagri Rajasthani", "Banjari", "Bhadrawahi", "Bhagoria", 
    "Bharmauri/Gaddi", "Bhojpuri", "Bishnoi", "Brajbhasha", "Bundeli/Bundel khandi", 
    "Chambeali/Chamrali", "Chhattisgarhi", "Churahi", "Dhundhari", "Garhwali", "Gawari", 
    "Gojri/Gujjari/Gujar", "Handuri", "Hara/Harauti", "Haryanvi", "Hindi", 
    "Jaunpuri/Jaunsari", "Kangri", "Khari Boli", "Khortha/Khotta", "Kulvi", "Kumauni", 
    "Kurmali Thar", "Lamani/Lambadi", "Laria", "Lodhi", "Magadhi/Magahi", "Malvi", 
    "Mandeali", "Marwari", "Mewari", "Mewati", "Nagpuria", "Nimadi", "Padari", "Pahari", 
    "Palmuha", "Panch Pargania", "Pando/Pandwani", "Pangwali", "Pawari/Powari", 
    "Puran/Puran Bhasha", "Rajasthani", "Sadan/Sadri", "Sirmauri", "Sondwari", "Sugali", 
    "Surgujia", "Surjapuri", "Badaga", "Kannada", "Kuruba/Kurumba", "Prakritha/Prakritha Bhasha", 
    "Kashmiri", "Kishtwari", "Siraji", "Dardi", "Konkani", "Kudubi/Kudumbi", "Malwani", 
    "Nawait", "Gorboli/Goru/Gorwani", "Maithili", "Purbi Maithili", "Thati", "Tharu", 
    "Malayalam", "Pania", "Yerava", "Manipuri", "Are", "Koli", "Marathi", "Nepali", 
    "Bhatri", "Bhuyan", "Bhumijali", "Desia", "Odia", "Proja (Ori)", "Relli", 
    "Sambalpuri", "Bagri", "Bhateli", "Bilaspuri Kahluri", "Punjabi", "Sanskrit", 
    "Karmali", "Mahili", "Santali", "Bhatia", "Kachchhi", "Sindhi", "Irula/Irular Mozhi", 
    "Kaikadi", "Korava", "Tamil", "Yerukala/Yerukula", "Telugu", "Vadari", "Urdu", 
    "Bhansari", "Adi", "Adi Gallong/Gallong", "Adi Miniyong/Miniyong", "Talgalo", 
    "Afghani/Kabuli/Pashto", "Anal", "Angami", "Ao", "Chungli", "Mongsen", "Arabic/Arbi", 
    "Balti", "Baori", "Barel", "Bhilali", "Bhili/Bhilodi", "Chodhari", "Dhodia", 
    "Gamti/Gavit", "Garasia", "Kokna/Kokni/Kukna", "Mawchi", "Paradhi", "Pawri", "Rathi", 
    "Tadavi", "Varli", "Vasava", "Wagdi", "Bhotia", "Bauti", "Bhumij", 
    "Bishnupriya Manipuri/Manipuri Bishnupriya", "Chakhesang", "Chakru/Chokri", "Chang", 
    "Coorgi/Kodagu", "Kodava", "Deori", "Dimasa", "Gadaba", "Gangte", "Garo", "Dorli", 
    "Gondi", "Kalari", "Maria/Muria", "Halabi", "Halam", "Hmar", "Ho", "Lohara", "Jatapu", 
    "Juang", "Kabui", "Rongmei", "Karbi/Mikir", "Ahirani", "Dangi", "Gujari", "Khandeshi", 
    "Kharia", "Khasi", "Lyngngam", "Pnar/Synteng", "War", "Khezha", "Khiemnungan", 
    "Khond/Kondh", "Kuvi", "Kinnauri", "Kisan", "Koch", "Koda/Kora", "Kolami", "Kom", 
    "Kodu", "Konda", "Konyak", "Korku", "Muwasi", "Koraku", "Korwa", "Koya", "Kui", 
    "Kuki", "Kurukh/Oraon", "Ladakhi", "Lahauli", "Bahawal Puri", "Hindi Multani", "Mara", 
    "Lalung", "Lepcha", "Liangmei", "Limbu", "Lotha", "Lushai/Mizo", "Pahariya", "Kulehiya", 
    "Mao", "Paola", "Maram", "Maring", "Miri/Mishing", "Mishmi", "Mogh", "Monpa", "Kol", 
    "Munda", "Mundari", "Nicobarese", "Apatani", "Nissi/Dafla", "Tagin", "Nocte", "Paite", 
    "Dhurwa", "Pawi", "Phom", "Pochury", "Rabha", "Rai", "Rengma", "Sangtam", "Savara", 
    "Sema", "Sherpa", "Shina", "Tamang", "Tangkhul", "Tutcha Tangsa", "Thado", "Tibetan", 
    "Purkhi", "Kokbarak", "Reang", "Tripuri", "Tulu", "Vaiphei", "Wancho", "Chirr", 
    "Tikhir", "Yimchungre", "Zeliang", "Zemi", "Zou"
];
// Comprehensive skills database for auto-suggestions
const skillsDatabase = [
    // Programming Languages
    'JavaScript', 'Java', 'Python', 'C++', 'C#', 'C', 'PHP', 'Ruby', 'Go', 'Rust',
    'Swift', 'Kotlin', 'TypeScript', 'Scala', 'R', 'MATLAB', 'Perl', 'Dart', 'Lua',
    
    // Web Technologies
    'HTML', 'CSS', 'React', 'Angular', 'Vue.js', 'Node.js', 'Express.js', 'Next.js',
    'Nuxt.js', 'Svelte', 'jQuery', 'Bootstrap', 'Tailwind CSS', 'SASS', 'LESS',
    'Webpack', 'Vite', 'Parcel', 'Gulp', 'Grunt',
    
    // Mobile Development
    'React Native', 'Flutter', 'Android Development', 'iOS Development', 'Xamarin',
    'Ionic', 'Cordova', 'PhoneGap',
    
    // Databases
    'MySQL', 'PostgreSQL', 'MongoDB', 'SQLite', 'Oracle', 'SQL Server', 'Redis',
    'Cassandra', 'DynamoDB', 'Firebase', 'Elasticsearch', 'Neo4j',
    
    // Cloud & DevOps
    'AWS', 'Azure', 'Google Cloud', 'Docker', 'Kubernetes', 'Jenkins', 'GitLab CI',
    'GitHub Actions', 'Terraform', 'Ansible', 'Chef', 'Puppet', 'Vagrant',
    
    // Data Science & AI
    'Machine Learning', 'Deep Learning', 'Data Analysis', 'Data Visualization',
    'TensorFlow', 'PyTorch', 'Scikit-learn', 'Pandas', 'NumPy', 'Matplotlib',
    'Seaborn', 'Plotly', 'Tableau', 'Power BI', 'Apache Spark', 'Hadoop',
    
    // Design & UI/UX
    'UI/UX Design', 'Figma', 'Adobe Photoshop', 'Adobe Illustrator', 'Adobe XD',
    'Sketch', 'InVision', 'Canva', 'Graphic Design', 'Web Design',
    
    // Testing
    'Unit Testing', 'Integration Testing', 'Selenium', 'Jest', 'Cypress', 'Mocha',
    'Chai', 'JUnit', 'TestNG', 'Postman', 'API Testing',
    
    // Version Control
    'Git', 'GitHub', 'GitLab', 'Bitbucket', 'SVN',
    
    // Project Management
    'Agile', 'Scrum', 'Kanban', 'Jira', 'Trello', 'Asana', 'Monday.com',
    
    // Other Technical Skills
    'REST API', 'GraphQL', 'Microservices', 'System Design', 'Software Architecture',
    'Cybersecurity', 'Network Security', 'Blockchain', 'IoT', 'AR/VR',
    
    // Soft Skills
    'Communication', 'Leadership', 'Team Management', 'Problem Solving',
    'Critical Thinking', 'Time Management', 'Project Management', 'Public Speaking',
    'Presentation Skills', 'Negotiation', 'Customer Service', 'Sales',
    
    // Business Skills
    'Digital Marketing', 'SEO', 'SEM', 'Social Media Marketing', 'Content Marketing',
    'Email Marketing', 'Analytics', 'Business Analysis', 'Financial Analysis',
    'Market Research', 'Strategic Planning'
];
// Notification System
function showNotification(message, type = 'info', duration = 5000) {
    // Create notification container if it doesn't exist
    let notificationContainer = document.getElementById('notification-container');
    if (!notificationContainer) {
        notificationContainer = document.createElement('div');
        notificationContainer.id = 'notification-container';
        notificationContainer.style.cssText = `
            position: fixed;
            top: 20px;
            right: 20px;
            z-index: 9999;
            display: flex;
            flex-direction: column;
            gap: 10px;
            max-width: 400px;
        `;
        document.body.appendChild(notificationContainer);
    }
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.style.cssText = `
        padding: 16px 20px;
        border-radius: 8px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
        display: flex;
        align-items: center;
        gap: 12px;
        min-width: 300px;
        animation: slideIn 0.3s ease-out;
        transition: all 0.3s ease;
        position: relative;
        overflow: hidden;
    `;
    // Set background and text colors based on type
    switch (type) {
        case 'success':
            notification.style.backgroundColor = '#10b981';
            notification.style.color = 'white';
            break;
        case 'error':
            notification.style.backgroundColor = '#ef4444';
            notification.style.color = 'white';
            break;
        case 'warning':
            notification.style.backgroundColor = '#f59e0b';
            notification.style.color = 'white';
            break;
        default:
            notification.style.backgroundColor = '#3b82f6';
            notification.style.color = 'white';
    }
    // Add icon based on type
    const icon = document.createElement('span');
    icon.style.cssText = `
        font-size: 20px;
        flex-shrink: 0;
    `;
    switch (type) {
        case 'success':
            icon.innerHTML = '✓';
            break;
        case 'error':
            icon.innerHTML = '✕';
            break;
        case 'warning':
            icon.innerHTML = '⚠';
            break;
        default:
            icon.innerHTML = 'ℹ';
    }
    // Add message
    const messageElement = document.createElement('div');
    messageElement.style.cssText = `
        flex: 1;
        font-size: 14px;
        line-height: 1.4;
    `;
    messageElement.textContent = message;
    // Add close button
    const closeButton = document.createElement('button');
    closeButton.innerHTML = '×';
    closeButton.style.cssText = `
        background: none;
        border: none;
        color: inherit;
        font-size: 20px;
        cursor: pointer;
        padding: 0;
        margin-left: 10px;
        opacity: 0.8;
        transition: opacity 0.2s;
    `;
    closeButton.onmouseover = () => closeButton.style.opacity = '1';
    closeButton.onmouseout = () => closeButton.style.opacity = '0.8';
    closeButton.onclick = () => removeNotification(notification);
    // Add progress bar
    const progressBar = document.createElement('div');
    progressBar.style.cssText = `
        position: absolute;
        bottom: 0;
        left: 0;
        height: 3px;
        background: rgba(255, 255, 255, 0.3);
        width: 100%;
        animation: progress ${duration}ms linear;
    `;
    // Assemble notification
    notification.appendChild(icon);
    notification.appendChild(messageElement);
    notification.appendChild(closeButton);
    notification.appendChild(progressBar);
    // Add to container
    notificationContainer.appendChild(notification);
    // Auto-remove after duration
    setTimeout(() => {
        removeNotification(notification);
    }, duration);
    return notification;
}
function removeNotification(notification) {
    notification.style.animation = 'slideOut 0.3s ease-in';
    setTimeout(() => {
        if (notification.parentNode) {
            notification.parentNode.removeChild(notification);
        }
    }, 300);
}
// Add CSS animations for notifications
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }
    
    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(100%);
            opacity: 0;
        }
    }
    
    @keyframes progress {
        from {
            width: 100%;
        }
        to {
            width: 0%;
        }
    }
`;
document.head.appendChild(style);
// Notification Badge Functions
function updateNotificationBadge(count) {
    console.log('Updating notification badge with count:', count);
    
    // Update desktop notification badge
    const desktopBadge = document.getElementById('desktop-notification-count');
    if (desktopBadge) {
        if (count > 0) {
            desktopBadge.textContent = count > 99 ? '99+' : count;
            desktopBadge.classList.remove('hidden');
            console.log('Desktop badge updated');
        } else {
            desktopBadge.classList.add('hidden');
            console.log('Desktop badge hidden');
        }
    } else {
        console.warn('Desktop notification badge not found');
    }
    
    // Update mobile notification badge
    const mobileBadge = document.getElementById('mobile-notification-count');
    if (mobileBadge) {
        if (count > 0) {
            mobileBadge.textContent = count > 99 ? '99+' : count;
            mobileBadge.classList.remove('hidden');
            console.log('Mobile badge updated');
        } else {
            mobileBadge.classList.add('hidden');
            console.log('Mobile badge hidden');
        }
    } else {
        console.warn('Mobile notification badge not found');
    }
}
// Function to fetch notification counts
window.fetchNotificationCounts = async function() {
    let loggedInUser = sessionStorage.getItem('loggedInUser') || localStorage.getItem('persistentLogin');
    if (!loggedInUser) {
        window.updateNotificationBadge(0);
        return;
    }
    const user = JSON.parse(loggedInUser);
    const userId = user.id;
    if (!userId) {
        window.updateNotificationBadge(0);
        return;
    }
    try {
        const response = await fetch('http://localhost:8080/api/user/notifications/counts', {
            headers: { 'X-User-Id': userId.toString() }
        });
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        const data = await response.json();
        window.updateNotificationBadge(data.unread || 0);
    } catch (error) {
        console.error('Error fetching notification count:', error);
        window.updateNotificationBadge(0);
    }
};
window.updateNotificationBadge = function(count) {
    const desktopBadge = document.getElementById('desktop-notification-count');
    const mobileBadge = document.getElementById('mobile-notification-count');
    if (count > 0) {
        if (desktopBadge) {
            desktopBadge.textContent = count > 99 ? '99+' : count;
            desktopBadge.classList.add('visible');
            desktopBadge.classList.remove('hidden');
        }
        if (mobileBadge) {
            mobileBadge.textContent = count > 99 ? '99+' : count;
            mobileBadge.classList.add('visible');
            mobileBadge.classList.remove('hidden');
        }
    } else {
        if (desktopBadge) {
            desktopBadge.classList.remove('visible');
            desktopBadge.classList.add('hidden');
        }
        if (mobileBadge) {
            mobileBadge.classList.remove('visible');
            mobileBadge.classList.add('hidden');
        }
    }
};
// Logout function with modal confirmation
function performLogout() {
    console.log('Logout function called');
    
    // Clear all user data from both storage types
    localStorage.removeItem("loggedInUser");
    localStorage.removeItem("persistentLogin");
    sessionStorage.removeItem("loggedInUser");
    sessionStorage.removeItem("userId");
    localStorage.removeItem("registeredUser");
    localStorage.removeItem("tempGoogleUser");
    
    // Clear any other user-related data
    const keysToRemove = [];
    for (let i = 0; i < localStorage.length; i++) {
        const key = localStorage.key(i);
        if (key.includes('user') || key.includes('auth') || key.includes('token') || key.includes('login')) {
            keysToRemove.push(key);
        }
    }
    keysToRemove.forEach(key => localStorage.removeItem(key));
    
    for (let i = 0; i < sessionStorage.length; i++) {
        const key = sessionStorage.key(i);
        if (key.includes('user') || key.includes('auth') || key.includes('token') || key.includes('login')) {
            sessionStorage.removeItem(key);
        }
    }
    
    // Set logout flags
    sessionStorage.setItem('logoutFlag', 'true');
    localStorage.setItem('logoutFlag', 'true');
    
    console.log('User data cleared, redirecting to login page');
    
    // Force redirect to login page
    const timestamp = new Date().getTime();
    window.location.replace("login.html?t=" + timestamp + "&logout=true");
}
// Function to show logout confirmation modal
function showLogoutModal() {
    const logoutModal = document.getElementById('logout-modal');
    if (logoutModal) {
        logoutModal.style.display = 'flex';
    }
}
// Function to hide logout confirmation modal
function hideLogoutModal() {
    const logoutModal = document.getElementById('logout-modal');
    if (logoutModal) {
        logoutModal.style.display = 'none';
    }
}
// Function to setup logout modal event listeners
function setupLogoutModal() {
    const logoutModal = document.getElementById('logout-modal');
    const logoutCancelBtn = document.getElementById('logout-cancel-btn');
    const logoutConfirmBtn = document.getElementById('logout-confirm-btn');
    
    // Cancel button - just close the modal
    if (logoutCancelBtn) {
        logoutCancelBtn.addEventListener('click', function() {
            hideLogoutModal();
        });
    }
    
    // Confirm button - perform logout
    if (logoutConfirmBtn) {
        logoutConfirmBtn.addEventListener('click', function() {
            hideLogoutModal();
            performLogout();
        });
    }
    
    // Close modal when clicking outside
    if (logoutModal) {
        logoutModal.addEventListener('click', function(e) {
            if (e.target === logoutModal) {
                hideLogoutModal();
            }
        });
    }
}
// Function to setup logout button
function setupLogoutButton() {
    console.log('Setting up logout button');
    
    // Try multiple selectors to find the logout button
    const logoutBtn = document.getElementById('logout-btn') || 
                      document.querySelector('[data-page="logout"]') ||
                      document.querySelector('.logout-btn');
    
    if (logoutBtn) {
        // Remove any existing event listeners
        logoutBtn.replaceWith(logoutBtn.cloneNode(true));
        
        // Get the fresh element
        const freshLogoutBtn = document.getElementById('logout-btn') || 
                              document.querySelector('[data-page="logout"]') ||
                              document.querySelector('.logout-btn');
        
        // Add event listener to show modal instead of direct logout
        freshLogoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            showLogoutModal();
        });
        
        console.log('Logout button setup complete');
    } else {
        console.error('Logout button not found');
    }
}
// Language dropdown search functions
function filterLanguages(input) {
    const searchTerm = input.value.toLowerCase();
    const index = input.getAttribute('data-index');
    const dropdown = document.getElementById(`languageOptions_${index}`);
    const options = dropdown.querySelectorAll('.language-option');
    
    let hasVisibleOptions = false;
    options.forEach(option => {
        const text = option.textContent.toLowerCase();
        if (text.includes(searchTerm)) {
            option.style.display = 'block';
            hasVisibleOptions = true;
        } else {
            option.style.display = 'none';
        }
    });
    
    dropdown.style.display = hasVisibleOptions ? 'block' : 'none';
}
function toggleLanguageDropdown(input) {
    const index = parseInt(input.getAttribute('data-index'));
    
    // Don't show dropdown for the first language (English - readonly)
    if (index === 0) {
        return;
    }
    
    const dropdown = document.getElementById(`languageOptions_${index}`);
    dropdown.style.display = dropdown.style.display === 'none' ? 'block' : 'none';
    
    // Show all options when clicked
    const options = dropdown.querySelectorAll('.language-option');
    options.forEach(option => {
        option.style.display = 'block';
    });
}
function selectLanguage(language, index) {
    const input = document.querySelector(`[data-index="${index}"].language-search`);
    const hiddenInput = input.nextElementSibling;
    const dropdown = document.getElementById(`languageOptions_${index}`);
    
    // Don't allow changing the first language from English
    if (index === 0 && language !== 'English') {
        dropdown.style.display = 'none';
        return;
    }
    
    input.value = language;
    hiddenInput.value = language;
    dropdown.style.display = 'none';
}
function hideLanguageDropdown(input) {
    const index = input.getAttribute('data-index');
    const dropdown = document.getElementById(`languageOptions_${index}`);
    
    // Delay hiding to allow for option selection
    setTimeout(() => {
        dropdown.style.display = 'none';
    }, 200);
}
// Skills auto-suggestion functions
function filterSkills(input) {
    const index = input.getAttribute('data-index');
    const dropdown = document.getElementById(`skillOptions_${index}`);
    const searchTerm = input.value.toLowerCase();
    
    if (!dropdown) return;
    
    // Clear previous options
    dropdown.innerHTML = '';
    
    if (searchTerm.length === 0) {
        // Show all skills if no search term
        skillsDatabase.forEach(skill => {
            const option = document.createElement('div');
            option.className = 'skill-option';
            option.textContent = skill;
            option.onclick = () => selectSkill(skill, index);
            dropdown.appendChild(option);
        });
    } else {
        // Filter skills based on search term
        const filteredSkills = skillsDatabase.filter(skill => 
            skill.toLowerCase().includes(searchTerm)
        );
        
        if (filteredSkills.length > 0) {
            filteredSkills.forEach(skill => {
                const option = document.createElement('div');
                option.className = 'skill-option';
                option.textContent = skill;
                option.onclick = () => selectSkill(skill, index);
                dropdown.appendChild(option);
            });
        } else {
            // Show "No matches found" message
            const option = document.createElement('div');
            option.className = 'skill-option';
            option.textContent = 'No matches found';
            option.style.color = '#6b7280';
            option.style.fontStyle = 'italic';
            dropdown.appendChild(option);
        }
    }
    
    dropdown.style.display = 'block';
}
function toggleSkillDropdown(input) {
    const index = input.getAttribute('data-index');
    const dropdown = document.getElementById(`skillOptions_${index}`);
    
    if (!dropdown) return;
    
    if (dropdown.style.display === 'none' || dropdown.style.display === '') {
        // Initialize with all skills if dropdown is empty
        if (dropdown.children.length === 0) {
            skillsDatabase.forEach(skill => {
                const option = document.createElement('div');
                option.className = 'skill-option';
                option.textContent = skill;
                option.onclick = () => selectSkill(skill, index);
                dropdown.appendChild(option);
            });
        }
        dropdown.style.display = 'block';
    } else {
        dropdown.style.display = 'none';
    }
}
function selectSkill(skill, index) {
    const input = document.querySelector(`[data-index="${index}"].skill-search`);
    const dropdown = document.getElementById(`skillOptions_${index}`);
    
    if (input) {
        input.value = skill;
    }
    
    if (dropdown) {
        dropdown.style.display = 'none';
    }
}
function hideSkillDropdown(input) {
    setTimeout(() => {
        const index = input.getAttribute('data-index');
        const dropdown = document.getElementById(`skillOptions_${index}`);
        if (dropdown) {
            dropdown.style.display = 'none';
        }
    }, 200);
}
// DOB Validation Function
function validateDateOfBirth(dobInput) {
    const dob = new Date(dobInput.value);
    const today = new Date();
    const age = today.getFullYear() - dob.getFullYear();
    const monthDiff = today.getMonth() - dob.getMonth();
    
    // Calculate exact age
    let exactAge = age;
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < dob.getDate())) {
        exactAge--;
    }
    
    // Remove existing error message
    const existingError = dobInput.parentNode.querySelector('.age-error');
    if (existingError) {
        existingError.remove();
    }
    
    // Check if age is less than 15
    if (exactAge < 15) {
        const errorDiv = document.createElement('div');
        errorDiv.className = 'age-error';
        errorDiv.style.color = '#ef4444';
        errorDiv.style.fontSize = '12px';
        errorDiv.style.marginTop = '4px';
        errorDiv.textContent = 'You must be at least 15 years old to register.';
        dobInput.parentNode.appendChild(errorDiv);
        dobInput.setCustomValidity('You must be at least 15 years old to register.');
        return false;
    } else {
        dobInput.setCustomValidity('');
        return true;
    }
}
// Function to set maximum date for DOB (15 years ago from today)
function setDOBMaxDate() {
    const dobInput = document.getElementById('dob');
    if (dobInput) {
        const today = new Date();
        const maxDate = new Date(today.getFullYear() - 15, today.getMonth(), today.getDate());
        const maxDateString = maxDate.toISOString().split('T')[0];
        dobInput.setAttribute('max', maxDateString);
        
        // Add event listener for validation
        dobInput.addEventListener('change', function() {
            validateDateOfBirth(this);
        });
        
        dobInput.addEventListener('blur', function() {
            validateDateOfBirth(this);
        });
    }
}
// Phone Number Validation Functions
function validatePhoneNumber(phoneInput, countryCodeSelect) {
    const phoneNumber = phoneInput.value.replace(/\D/g, ''); // Remove non-digits
    const selectedOption = countryCodeSelect.options[countryCodeSelect.selectedIndex];
    const requiredDigits = parseInt(selectedOption.getAttribute('data-digits'));
    const countryCode = selectedOption.value;
    
    const messageDiv = document.getElementById('phoneValidationMessage');
    
    // Clear previous validation
    phoneInput.setCustomValidity('');
    messageDiv.className = 'phone-validation-message';
    
    if (phoneNumber.length === 0) {
        messageDiv.textContent = `Enter ${requiredDigits} digits for ${countryCode}`;
        messageDiv.classList.add('info');
        return false;
    }
    
    if (phoneNumber.length !== requiredDigits) {
        const message = `${countryCode} requires exactly ${requiredDigits} digits. You entered ${phoneNumber.length} digits.`;
        messageDiv.textContent = message;
        messageDiv.classList.add('error');
        phoneInput.setCustomValidity(message);
        return false;
    }
    
    // Validate phone number format
    phoneInput.value = phoneNumber; // Clean the input
    messageDiv.textContent = '';
    messageDiv.className = 'phone-validation-message';
    return true;
}
function setupPhoneValidation() {
    const phoneInput = document.getElementById('phone');
    const countryCodeSelect = document.getElementById('countryCode');
    
    if (phoneInput && countryCodeSelect) {
        // Update placeholder and validation message when country changes
        countryCodeSelect.addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            const requiredDigits = selectedOption.getAttribute('data-digits');
            const countryCode = selectedOption.value;
            
            phoneInput.placeholder = `Enter ${requiredDigits} digits`;
            
            // Clear phone input when country changes
            phoneInput.value = '';
            
            const messageDiv = document.getElementById('phoneValidationMessage');
            messageDiv.textContent = `Enter ${requiredDigits} digits for ${countryCode}`;
            messageDiv.className = 'phone-validation-message info';
        });
        
        // Validate on input
        phoneInput.addEventListener('input', function() {
            // Allow only digits
            this.value = this.value.replace(/\D/g, '');
            validatePhoneNumber(this, countryCodeSelect);
        });
        
        // Validate on blur
        phoneInput.addEventListener('blur', function() {
            validatePhoneNumber(this, countryCodeSelect);
        });
        
        // Initial setup
        const selectedOption = countryCodeSelect.options[countryCodeSelect.selectedIndex];
        const requiredDigits = selectedOption.getAttribute('data-digits');
        const countryCode = selectedOption.value;
        phoneInput.placeholder = `Enter ${requiredDigits} digits`;
        
        const messageDiv = document.getElementById('phoneValidationMessage');
        messageDiv.textContent = `Enter ${requiredDigits} digits for ${countryCode}`;
        messageDiv.className = 'phone-validation-message info';
    }
}
// Character Counter Functions
function updateCharacterCount(textarea, maxLength = 250) {
    const currentLength = textarea.value.length;
    const remaining = maxLength - currentLength;
    const counterId = textarea.id + '_counter';
    let counter = document.getElementById(counterId);
    
    if (!counter) {
        // Create counter if it doesn't exist
        counter = document.createElement('div');
        counter.id = counterId;
        counter.className = 'counter-info';
        counter.innerHTML = `<span class="counter-text">${currentLength}/${maxLength}</span>`;
        textarea.parentNode.appendChild(counter);
        textarea.parentNode.classList.add('has-counter');
    }
    
    const counterText = counter.querySelector('.counter-text');
    counterText.textContent = `${currentLength}/${maxLength}`;
    
    // Remove existing classes
    counterText.classList.remove('warning', 'danger');
    
    // Add warning/danger classes based on remaining characters
    if (remaining <= 20 && remaining > 0) {
        counterText.classList.add('warning');
    } else if (remaining <= 0) {
        counterText.classList.add('danger');
    }
}
function initializeCharacterCounters() {
    // Initialize counter for Professional Objective
    const objectiveField = document.getElementById('objective');
    if (objectiveField) {
        updateCharacterCount(objectiveField, 250);
    }
    
    // Initialize counters for dynamic description fields
    const descriptionFields = document.querySelectorAll('textarea[name*="[description]"]');
    descriptionFields.forEach(field => {
        if (!field.id) {
            field.id = 'desc_' + Math.random().toString(36).substr(2, 9);
        }
        updateCharacterCount(field, 250);
    });
}
function addCharacterLimitToTextarea(textarea, maxLength = 250) {
    textarea.setAttribute('maxlength', maxLength);
    textarea.classList.add('character-limit-textarea');
    
    // Generate unique ID if not present
    if (!textarea.id) {
        textarea.id = 'textarea_' + Math.random().toString(36).substr(2, 9);
    }
    
    // Add event listeners
    textarea.addEventListener('input', function() {
        updateCharacterCount(this, maxLength);
    });
    
    textarea.addEventListener('keyup', function() {
        updateCharacterCount(this, maxLength);
    });
    
    // Initialize counter
    updateCharacterCount(textarea, maxLength);
}
// Function to render profile details
function renderProfile(userData) {
    const profileDetails = document.getElementById("profile-details");
    if (!profileDetails) return;
    
    // For profile photo and signature, we'll use the API endpoints
    const profilePhotoUrl = userData.profilePhoto ? `${API_BASE_URL}/api/user/profile/photo/${userData.id}` : '';
    const signatureUrl = userData.signature ? `${API_BASE_URL}/api/user/profile/signature/${userData.id}` : '';
    
    profileDetails.innerHTML = `
        <div class="grid-profile-container">
            <div class="profile-grid">
                
                <!-- Row 1, Cell 1: Photo and Personal Information -->
                <div class="grid-cell photo-personal-cell">
                    <div class="profile-photo-circle">
                        ${profilePhotoUrl ? `
                            <img src="${profilePhotoUrl}" alt="Profile Photo" onerror="this.src=''; this.alt='Photo not available'">
                        ` : `
                            photo
                        `}
                    </div>
                    <h3 class="grid-section-title">personal information</h3>
                    <div class="personal-info-grid">
                        <div><strong>Full Name:</strong> ${userData.firstName} ${userData.lastName || ''}</div>
                        ${userData.email ? `<div><strong>Email:</strong> ${userData.email}</div>` : ''}
                        ${userData.countryCode ? `<div><strong>Phone:</strong> ${userData.countryCode} ${userData.mobile || ''}</div>` : userData.mobile ? `<div><strong>Phone:</strong> ${userData.mobile}</div>` : ''}
                        ${userData.address ? `<div><strong>Address:</strong> ${userData.address}</div>` : ''}
                        ${userData.dob ? `<div><strong>Date of Birth:</strong> ${new Date(userData.dob).toLocaleDateString()}</div>` : ''}
                        ${userData.fatherName ? `<div><strong>Father's Name:</strong> ${userData.fatherName}</div>` : ''}
                        ${userData.motherName ? `<div><strong>Mother's Name:</strong> ${userData.motherName}</div>` : ''}
                        ${userData.objective ? `<div><strong>Professional Objective:</strong> ${userData.objective}</div>` : ''}
                    </div>
                </div>
                
                <!-- Row 1, Cell 2: Education -->
                <div class="grid-cell">
                    <h3 class="grid-section-title">education</h3>
                    <div class="grid-section-content">
                        ${userData.education && userData.education.length ? userData.education.map(item => `
                            <div class="grid-item">
                                <div class="grid-item-title"><strong>Degree:</strong> ${item.degree}, <strong>Branch:</strong> ${item.branch}</div>
                                <div class="grid-item-subtitle"><strong>Institution:</strong> ${item.institution_name}</div>
                                ${item.grade ? `<div class="grid-item-description"><strong>Grade:</strong> ${item.grade}</div>` : ''}
                                <div class="grid-item-date"><strong>Duration:</strong> ${item.start_year ? new Date(item.start_year).getFullYear() : ''} - ${item.end_year ? new Date(item.end_year).getFullYear() : ''}</div>
                            </div>
                        `).join('') : '<div>No education data available</div>'}
                    </div>
                </div>
                
                <!-- Row 2, Cell 1: Skills -->
                <div class="grid-cell">
                    <h3 class="grid-section-title">skills</h3>
                    <div class="grid-section-content">
                        ${userData.skills && userData.skills.length ? `
                            <div class="grid-skills">
                                ${userData.skills.map(item => `
                                    <span class="grid-skill-item">
                                        <strong>${item.skill_name}</strong>
                                        ${item.proficiency_level ? `<span class="skill-level">(${item.proficiency_level})</span>` : ''}
                                    </span>
                                `).join('')}
                            </div>
                        ` : '<div>No skills data available</div>'}
                    </div>
                </div>
                
                <!-- Row 2, Cell 2: Work Experience -->
                <div class="grid-cell">
                    <h3 class="grid-section-title">work experience</h3>
                    <div class="grid-section-content">
                        ${userData.experience && userData.experience.length ? userData.experience.map(item => `
                            <div class="grid-item">
                                <div class="grid-item-title"><strong>Role:</strong> ${item.role}</div>
                                <div class="grid-item-subtitle"><strong>Company:</strong> ${item.company_name}</div>
                                ${item.description ? `<div class="grid-item-description"><strong>Description:</strong> ${item.description}</div>` : ''}
                                <div class="grid-item-date">
                                    <strong>Duration:</strong> ${item.start_date ? new Date(item.start_date).toLocaleDateString() : ''} - 
                                    ${item.is_current ? 'Present' : (item.end_date ? new Date(item.end_date).toLocaleDateString() : '')}
                                </div>
                            </div>
                        `).join('') : '<div>No work experience data available</div>'}
                    </div>
                </div>
                
                <!-- Row 3, Cell 1: Certificates -->
                <div class="grid-cell">
                    <h3 class="grid-section-title">certificates</h3>
                    <div class="grid-section-content">
                        ${userData.certifications && userData.certifications.length ? userData.certifications.map((item, index) => `
                            <div class="grid-item">
                                <div class="grid-item-title"><strong>Certificate:</strong> ${item.certificate_name}</div>
                                <div class="grid-item-subtitle"><strong>Issued By:</strong> ${item.issuing_organization}</div>
                                ${item.issue_date ? `<div class="grid-item-date"><strong>Issue Date:</strong> ${new Date(item.issue_date).toLocaleDateString()}</div>` : ''}
                                ${item.certificate_file && item.certificate_file === 'has_file' ? `
                                    <div class="grid-item-file">
                                        <strong>Certificate File:</strong>
                                        <a href="${API_BASE_URL}/api/user/profile/certificate/${userData.id}/${index}" download="${item.certificate_file_name || 'certificate'}" class="download-link">Download Certificate</a>
                                    </div>
                                ` : '<div class="file-error">Certificate file not available</div>'}
                            </div>
                        `).join('') : '<div>No certificates data available</div>'}
                    </div>
                </div>
                
                <!-- Row 3, Cell 2: Projects -->
                <div class="grid-cell">
                    <h3 class="grid-section-title">projects</h3>
                    <div class="grid-section-content">
                        ${userData.projects && userData.projects.length ? userData.projects.map(item => `
                            <div class="grid-item">
                                <div class="grid-item-title"><strong>Title:</strong> ${item.project_title}</div>
                                <div class="grid-item-subtitle"><strong>Technologies:</strong> ${item.technologies_used || 'Not specified'}</div>
                                ${item.description ? `<div class="grid-item-description"><strong>Description:</strong> ${item.description}</div>` : ''}
                                ${item.project_link ? `
                                    <div class="grid-item-link">
                                        <strong>Project Link:</strong>
                                        <a href="${item.project_link}" target="_blank" class="external-link">View Project</a>
                                    </div>
                                ` : ''}
                            </div>
                        `).join('') : '<div>No projects data available</div>'}
                    </div>
                </div>
                
                <!-- Row 4, Cell 1: Achievements -->
                <div class="grid-cell">
                    <h3 class="grid-section-title">achievements</h3>
                    <div class="grid-section-content">
                        ${userData.achievements && userData.achievements.length ? userData.achievements.map(item => `
                            <div class="grid-item">
                                <div class="grid-item-title"><strong>Title:</strong> ${item.title || 'Achievement'}</div>
                                ${item.date ? `<div class="grid-item-date"><strong>Date:</strong> ${new Date(item.date).toLocaleDateString()}</div>` : ''}
                                ${item.description ? `<div class="grid-item-description"><strong>Description:</strong> ${item.description}</div>` : ''}
                            </div>
                        `).join('') : '<div>No achievements data available</div>'}
                    </div>
                </div>
                
                <!-- Row 4, Cell 2: Languages, Hobbies, References and Signature -->
                <div class="grid-cell">
                    <h3 class="grid-section-title">languages, hobbies and references</h3>
                    <div class="combined-section">
                        ${userData.languages && userData.languages.length ? `
                            <div class="mini-section">
                                <div class="mini-section-title">languages</div>
                                <div class="mini-section-content">
                                    ${userData.languages.map(item => `
                                        <div class="language-item">
                                            <strong>${item.language_name}</strong>
                                            ${item.proficiency_level ? `<span class="proficiency">(${item.proficiency_level})</span>` : ''}
                                        </div>
                                    `).join('')}
                                </div>
                            </div>
                        ` : ''}
                        
                        ${userData.hobbies && userData.hobbies.length ? `
                            <div class="mini-section">
                                <div class="mini-section-title">hobbies</div>
                                <div class="mini-section-content">
                                    ${userData.hobbies.map(item => `
                                        <div class="hobby-item">
                                            <strong>${item.hobby_name}</strong>
                                        </div>
                                    `).join('')}
                                </div>
                            </div>
                        ` : ''}
                        
                        ${userData.references && userData.references.length ? `
                            <div class="mini-section">
                                <div class="mini-section-title">references</div>
                                <div class="mini-section-content">
                                    ${userData.references.map(item => `
                                        <div class="reference-item">
                                            <div><strong>Name:</strong> ${item.reference_name}</div>
                                            ${item.relation ? `<div><strong>Relation:</strong> ${item.relation}</div>` : ''}
                                            ${item.contact_info ? `<div><strong>Contact:</strong> ${item.contact_info}</div>` : ''}
                                        </div>
                                    `).join('')}
                                </div>
                            </div>
                        ` : ''}
                        
                        ${signatureUrl ? `
                            <div class="mini-section">
                                <div class="mini-section-title">signature</div>
                                <div class="signature-container">
                                    <img src="${signatureUrl}" alt="Digital Signature" class="signature-image" onerror="this.src=''; this.alt='Signature not available'">
                                </div>
                            </div>
                        ` : ''}
                    </div>
                </div>
                
            </div>
        </div>
    `;
    // Revoke blob URLs when they're no longer needed
    setTimeout(() => {
        if (profilePhotoUrl) URL.revokeObjectURL(profilePhotoUrl);
        if (signatureUrl) URL.revokeObjectURL(signatureUrl);
    }, 1000);
}
// Function to toggle edit mode
function toggleEditMode(showEdit) {
    const profileDetails = document.getElementById("profile-details");
    const editForm = document.getElementById("edit-profile-form");
    const editBtn = document.getElementById("editProfileBtn");
    if (showEdit) {
        profileDetails.style.display = "none";
        editForm.classList.remove("hidden");
        editBtn.style.display = "none";
        
        // Clear form first to ensure fresh data loading
        clearFormData();
        
        // Populate form with current data
        populateForm();
        
        // Initialize character counter for Professional Objective after form is shown
        setTimeout(() => {
            const objectiveField = document.getElementById('objective');
            if (objectiveField) {
                addCharacterLimitToTextarea(objectiveField, 250);
            }
        }, 100);
    } else {
        profileDetails.style.display = "block";
        editForm.classList.add("hidden");
        editBtn.style.display = "inline-block";
    }
}
// Function to clear form data
function clearFormData() {
    // Clear all input fields
    const inputs = document.querySelectorAll('#edit-profile-form input, #edit-profile-form textarea, #edit-profile-form select');
    inputs.forEach(input => {
        if (input.type === 'checkbox') {
            input.checked = false;
        } else if (input.type === 'file') {
            input.value = '';
        } else {
            input.value = '';
        }
    });
    
    // Clear dynamic sections
    const dynamicSections = ['education', 'skills', 'experience', 'projects', 'certifications', 'achievements', 'languages', 'hobbies', 'references'];
    dynamicSections.forEach(section => {
        const container = document.getElementById(`${section}List`);
        if (container) {
            container.innerHTML = '';
        }
    });
    
    // Clear image preview displays
    ['profilePhoto', 'signature'].forEach(type => {
        const placeholder = document.getElementById(`${type}Placeholder`);
        const display = document.getElementById(`${type}Display`);
        const img = document.getElementById(`${type}Img`);
        const removeContainer = document.getElementById(`${type}RemoveContainer`);
        
        if (placeholder && display && img) {
            placeholder.style.display = 'flex';
            display.style.display = 'none';
            img.src = '';
            img.alt = '';
            
            // Hide the remove button container
            if (removeContainer) {
                removeContainer.style.display = 'none';
            }
        }
    });
    
    // Clear file storage
    profilePhotoFile = null;
    signatureFile = null;
    certificateFiles = {};
}
// Function to populate form with existing data
function populateForm() {
    if (!currentUser) return;
    
    // Populate personal details
    const fullNameField = document.getElementById("fullName");
    const emailField = document.getElementById("email");
    
    if (fullNameField) fullNameField.value = `${currentUser.firstName} ${currentUser.lastName || ''}`;
    if (emailField) emailField.value = currentUser.email || '';
    
    // Populate other fields
    const fields = ['dob', 'gender', 'address', 'fatherName', 'motherName', 'linkedinUrl', 'githubUrl', 'portfolioUrl', 'objective'];
    fields.forEach(field => {
        const element = document.getElementById(field);
        if (element && currentUser[field]) {
            element.value = currentUser[field];
        }
    });
    
    // Handle phone number and country code separately
    const countryCodeSelect = document.getElementById('countryCode');
    const phoneInput = document.getElementById('phone');
    
    if (currentUser.countryCode && countryCodeSelect) {
        countryCodeSelect.value = currentUser.countryCode;
    }
    
    if (currentUser.mobile && phoneInput) {
        phoneInput.value = currentUser.mobile;
    }
    
    // Populate dynamic sections
    populateDynamicSection('education', currentUser.education || []);
    populateDynamicSection('skills', currentUser.skills || []);
    populateDynamicSection('experience', currentUser.experience || []);
    populateDynamicSection('projects', currentUser.projects || []);
    populateDynamicSection('certifications', currentUser.certifications || []);
    populateDynamicSection('achievements', currentUser.achievements || []);
    populateDynamicSection('languages', currentUser.languages || []);
    populateDynamicSection('hobbies', currentUser.hobbies || []);
    populateDynamicSection('references', currentUser.references || []);
    
    // Populate image forms using API endpoints
    populateImageForms();
    
    // Initialize character counters after form is populated
    setTimeout(() => {
        initializeCharacterCounters();
    }, 100);
}
// Function to populate image forms
function populateImageForms() {
    // Populate profile photo
    if (currentUser.profilePhoto && currentUser.profilePhoto === 'has_photo') {
        const profilePhotoUrl = `${API_BASE_URL}/api/user/profile/photo/${currentUser.id}`;
        updateImagePreview('profilePhoto', profilePhotoUrl, 'Profile Photo');
        // Set a flag to indicate there's an existing photo
        profilePhotoFile = 'existing';
    }
    
    // Populate signature
    if (currentUser.signature && currentUser.signature === 'has_signature') {
        const signatureUrl = `${API_BASE_URL}/api/user/profile/signature/${currentUser.id}`;
        updateImagePreview('signature', signatureUrl, 'Digital Signature');
        // Set a flag to indicate there's an existing signature
        signatureFile = 'existing';
    }
}
// Function to populate dynamic sections
function populateDynamicSection(section, data = []) {
    data.forEach((item, index) => {
        addDynamicItem(section, item, index);
    });
}
// Function to switch tabs
function switchTab(tabName) {
    // Hide all tab contents
    document.querySelectorAll('.form-tab-content').forEach(content => {
        content.classList.remove('active');
    });
    
    // Remove active class from all tab buttons
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    
    // Show selected tab content
    const targetTab = document.getElementById(`${tabName}Tab`);
    if (targetTab) {
        targetTab.classList.add('active');
    }
    
    // Add active class to selected tab button
    const targetBtn = document.querySelector(`[data-tab="${tabName}"]`);
    if (targetBtn) {
        targetBtn.classList.add('active');
    }
    
    // Special handling for languages tab - auto-add English as default first language
    if (tabName === 'languages') {
        const languagesList = document.getElementById('languagesList');
        if (languagesList && languagesList.children.length === 0) {
            // Only add English if no languages exist yet
            addDynamicItem('languages', { language_name: 'English', proficiency_level: '' }, 0);
        }
    }
    
    // Special handling for experience tab - check optional state
    if (tabName === 'experience') {
        checkExperienceOptionalState();
    }
    
    // Special handling for achievements tab - check optional state
    if (tabName === 'achievements') {
        checkAchievementOptionalState();
    }
    
    // Update current tab index
    currentTabIndex = tabs.indexOf(tabName);
    updateNavigationButtons();
}
// Function to update navigation buttons
function updateNavigationButtons() {
    const prevBtn = document.getElementById("prevTabBtn");
    const nextBtn = document.getElementById("nextTabBtn");
    
    if (prevBtn) {
        prevBtn.disabled = currentTabIndex === 0;
    }
    
    if (nextBtn) {
        if (currentTabIndex === tabs.length - 1) {
            nextBtn.style.display = 'none';
        } else {
            nextBtn.style.display = 'inline-block';
        }
    }
}
// Helper function to generate year options
function generateYearOptions(selectedYear = '') {
    const currentYear = new Date().getFullYear();
    const startYear = 1980;
    let options = '<option value="">Select Year</option>';
    
    // Convert selectedYear to string if it's a number
    selectedYear = selectedYear.toString();
    
    for (let year = currentYear + 5; year >= startYear; year--) {
        const yearStr = year.toString();
        const selected = yearStr === selectedYear ? 'selected' : '';
        options += `<option value="${year}" ${selected}>${year}</option>`;
    }
    
    return options;
}
// Function to add dynamic items
function addDynamicItem(section, data = {}, index = null) {
    const container = document.getElementById(`${section}List`);
    if (!container) return;
    const itemIndex = index !== null ? index : container.children.length;
    let itemHTML = '';
    switch (section) {
        case 'education':
            // Check if this is the first education entry (10th grade)
            const is10thGrade = itemIndex === 0 || 
                (data.degree && (
                    data.degree.toLowerCase().includes('10th') || 
                    data.degree.toLowerCase().includes('tenth') ||
                    data.degree.toLowerCase().includes('secondary')
                ));
            
            itemHTML = `
                <div class="dynamic-item ${is10thGrade ? 'required-education' : ''}" data-index="${itemIndex}">
                    <div class="flex justify-between items-start mb-4">
                        <h4 class="text-md font-medium">
                            ${is10thGrade ? '10th Grade (Required)' : `Education ${itemIndex + 1}`}
                            ${is10thGrade ? '<span style="color: #ef4444; font-size: 12px; font-weight: normal;"> - Cannot be removed</span>' : ''}
                        </h4>
                        ${!is10thGrade ? `<button type="button" class="remove-btn" onclick="removeDynamicItem('${section}', ${itemIndex})">Remove</button>` : ''}
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Institution Name</label>
                            <input type="text" name="${section}[${itemIndex}][institution_name]" value="${data.institution_name || ''}" required>
                        </div>
                        <div class="form-group">
                            <label class="required">Degree</label>
                            <input type="text" 
                                   name="${section}[${itemIndex}][degree]" 
                                   value="${data.degree || (is10thGrade ? '10th Grade (Secondary)' : '')}" 
                                   ${is10thGrade ? 'readonly' : ''} 
                                   required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Branch/Field</label>
                            <input type="text" 
                                   name="${section}[${itemIndex}][branch]" 
                                   value="${data.branch || (is10thGrade ? 'General' : '')}" 
                                   ${is10thGrade ? 'readonly' : ''} 
                                   required>
                        </div>
                        <div class="form-group">
                            <label class="required">Start Year</label>
                            <select name="${section}[${itemIndex}][start_year]" required>
                                ${generateYearOptions(data.start_year ? data.start_year.toString() : '')}
                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">End Year</label>
                            <select name="${section}[${itemIndex}][end_year]" required>
                                ${generateYearOptions(data.end_year ? data.end_year.toString() : '')}
                            </select>
                        </div>
                        <div class="form-group">
                            <label class="required">Percentage/CGPA</label>
                            <input type="number" 
                                   name="${section}[${itemIndex}][grade]" 
                                   value="${data.grade || ''}"
                                   step="0.01"
                                   min="0"
                                   max="100"
                                   placeholder="Enter percentage (0-100) or CGPA"
                                   required>
                        </div>
                    </div>
                </div>
            `;
            break;
        case 'skills':
            itemHTML = `
                <div class="dynamic-item" data-index="${itemIndex}">
                    <div class="flex justify-between items-start mb-4">
                        <h4 class="text-md font-medium">Skill ${itemIndex + 1}</h4>
                        <button type="button" class="remove-btn" onclick="removeDynamicItem('${section}', ${itemIndex})">Remove</button>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Skill Name</label>
                            <div class="skill-input-container">
                                <input type="text" 
                                       class="skill-search" 
                                       name="${section}[${itemIndex}][skill_name]" 
                                       value="${data.skill_name || ''}" 
                                       placeholder="Type to search skills..." 
                                       autocomplete="off"
                                       data-index="${itemIndex}"
                                       onkeyup="filterSkills(this)"
                                       onclick="toggleSkillDropdown(this)"
                                       onblur="hideSkillDropdown(this)"
                                       required>
                                <div class="skill-options-dropdown" id="skillOptions_${itemIndex}" style="display: none;">
                                    ${skillsDatabase.map(skill => 
                                        `<div class="skill-option" onclick="selectSkill('${skill}', ${itemIndex})">${skill}</div>`
                                    ).join('')}
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="required">Proficiency Level</label>
                            <select name="${section}[${itemIndex}][proficiency_level]" required>
                                <option value="">Select Level</option>
                                <option value="Beginner" ${data.proficiency_level === 'Beginner' ? 'selected' : ''}>Beginner</option>
                                <option value="Intermediate" ${data.proficiency_level === 'Intermediate' ? 'selected' : ''}>Intermediate</option>
                                <option value="Advanced" ${data.proficiency_level === 'Advanced' ? 'selected' : ''}>Advanced</option>
                                <option value="Expert" ${data.proficiency_level === 'Expert' ? 'selected' : ''}>Expert</option>
                            </select>
                        </div>
                    </div>
                </div>
            `;
            break;
        case 'experience':
            itemHTML = `
                <div class="dynamic-item" data-index="${itemIndex}">
                    <div class="flex justify-between items-start mb-4">
                        <h4 class="text-md font-medium">Experience ${itemIndex + 1}</h4>
                        <button type="button" class="remove-btn" onclick="removeDynamicItem('${section}', ${itemIndex})">Remove</button>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Company Name</label>
                            <input type="text" name="${section}[${itemIndex}][company_name]" value="${data.company_name || ''}" required>
                        </div>
                        <div class="form-group">
                            <label class="required">Role/Position</label>
                            <input type="text" name="${section}[${itemIndex}][role]" value="${data.role || ''}" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Start Date</label>
                            <input type="date" name="${section}[${itemIndex}][start_date]" value="${data.start_date ? formatDateForInput(data.start_date) : ''}" required>
                        </div>
                        <div class="form-group">
                            <label>End Date</label>
                            <input type="date" name="${section}[${itemIndex}][end_date]" value="${data.end_date ? formatDateForInput(data.end_date) : ''}" ${data.is_current ? 'disabled' : ''}>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <div class="current-work-checkbox">
                                <input type="checkbox" id="current_${itemIndex}" name="${section}[${itemIndex}][is_current]" ${data.is_current ? 'checked' : ''} onchange="toggleEndDate(this, ${itemIndex})">
                                <label for="current_${itemIndex}">Currently working here</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea name="${section}[${itemIndex}][description]" id="exp_desc_${itemIndex}" rows="3" maxlength="250" class="character-limit-textarea" oninput="updateCharacterCount(this, 250)" onkeyup="updateCharacterCount(this, 250)">${data.description || ''}</textarea>
                    </div>
                </div>
            `;
            break;
        case 'projects':
            itemHTML = `
                <div class="dynamic-item" data-index="${itemIndex}">
                    <div class="flex justify-between items-start mb-4">
                        <h4 class="text-md font-medium">Project ${itemIndex + 1}</h4>
                        <button type="button" class="remove-btn" onclick="removeDynamicItem('${section}', ${itemIndex})">Remove</button>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Project Title</label>
                            <input type="text" name="${section}[${itemIndex}][project_title]" value="${data.project_title || ''}" required>
                        </div>
                        <div class="form-group">
                            <label>Technologies Used</label>
                            <input type="text" name="${section}[${itemIndex}][technologies_used]" value="${data.technologies_used || ''}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="required">Description</label>
                        <textarea name="${section}[${itemIndex}][description]" id="proj_desc_${itemIndex}" rows="3" maxlength="250" class="character-limit-textarea" required oninput="updateCharacterCount(this, 250)" onkeyup="updateCharacterCount(this, 250)">${data.description || ''}</textarea>
                    </div>
                    <div class="form-group">
                        <label>Project Link</label>
                        <input type="url" name="${section}[${itemIndex}][project_link]" value="${data.project_link || ''}">
                    </div>
                </div>
            `;
            break;
        case 'certifications':
            itemHTML = `
                <div class="dynamic-item" data-index="${itemIndex}">
                    <div class="flex justify-between items-start mb-4">
                        <h4 class="text-md font-medium">Certification ${itemIndex + 1}</h4>
                        <button type="button" class="remove-btn" onclick="removeDynamicItem('${section}', ${itemIndex})">Remove</button>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Certificate Name</label>
                            <input type="text" name="${section}[${itemIndex}][certificate_name]" value="${data.certificate_name || ''}" required>
                        </div>
                        <div class="form-group">
                            <label class="required">Issuing Organization</label>
                            <input type="text" name="${section}[${itemIndex}][issuing_organization]" value="${data.issuing_organization || ''}" required>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Issue Date</label>
                            <input type="date" name="${section}[${itemIndex}][issue_date]" value="${data.issue_date ? formatDateForInput(data.issue_date) : ''}" required>
                        </div>
                        <div class="form-group">
                            <label>Certificate Link</label>
                            <input type="url" name="${section}[${itemIndex}][certificate_link]" value="${data.certificate_link || ''}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Certificate File</label>
                        <div class="certification-file-upload" onclick="event.stopPropagation(); document.getElementById('certFile_${itemIndex}').click()">
                            <input type="file" 
                                   id="certFile_${itemIndex}" 
                                   name="${section}[${itemIndex}][certificate_file]" 
                                   accept=".pdf,.jpg,.jpeg,.png" 
                                   class="file-input"
                                   onchange="handleCertificationFileUpload(this, ${itemIndex})"
                                   style="display: none;">
                            <div id="certUploadPlaceholder_${itemIndex}" class="upload-placeholder" style="display: ${data.certificate_file === 'has_file' ? 'none' : 'flex'};">
                                <i data-lucide="file-plus" class="upload-icon"></i>
                                <span>Click to upload certificate file</span>
                                <small>Accepted formats: PDF, JPG, PNG (Max: 5MB)</small>
                            </div>
                            <div id="certFileInfo_${itemIndex}" class="file-info" style="display: ${data.certificate_file === 'has_file' ? 'block' : 'none'};">
                                <div class="flex items-center gap-2">
                                    <i data-lucide="file-text" class="w-4 h-4 text-green-600"></i>
                                    <span class="file-info-text flex-1">${data.certificate_file_name || 'Certificate file'}</span>
                                    <span class="file-size text-xs text-gray-500">Uploaded</span>
                                </div>
                            </div>
                        </div>
                        <div class="cert-remove-container" id="certRemoveContainer_${itemIndex}" style="display: ${data.certificate_file === 'has_file' ? 'block' : 'none'};">
                            <button type="button" onclick="removeCertificationFile(${itemIndex})" class="remove-image-btn">Remove Certificate</button>
                        </div>
                    </div>
                </div>
            `;
            break;
        case 'achievements':
            itemHTML = `
                <div class="dynamic-item" data-index="${itemIndex}">
                    <div class="flex justify-between items-start mb-4">
                        <h4 class="text-md font-medium">Achievement ${itemIndex + 1}</h4>
                        <button type="button" class="remove-btn" onclick="removeDynamicItem('${section}', ${itemIndex})">Remove</button>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label>Title</label>
                            <input type="text" name="${section}[${itemIndex}][title]" value="${data.title || ''}">
                        </div>
                        <div class="form-group">
                            <label>Date</label>
                            <input type="date" name="${section}[${itemIndex}][date]" value="${data.date ? formatDateForInput(data.date) : ''}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label>Description</label>
                        <textarea name="${section}[${itemIndex}][description]" id="ach_desc_${itemIndex}" rows="3" maxlength="250" class="character-limit-textarea" oninput="updateCharacterCount(this, 250)" onkeyup="updateCharacterCount(this, 250)">${data.description || ''}</textarea>
                    </div>
                </div>
            `;
            break;
        case 'languages':
            const languageOptions = motherTongues.map(language => 
                `<option value="${language}" ${data.language_name === language ? 'selected' : ''}>${language}</option>`
            ).join('');
            
            // Check if this is the first language (English should be default with proficiency)
            const isEnglishDefault = itemIndex === 0 && !data.language_name;
            const defaultLanguage = isEnglishDefault ? 'English' : (data.language_name || '');
            const showProficiency = itemIndex === 0; // Only show proficiency for the first language (English)
            
            itemHTML = `
                <div class="dynamic-item" data-index="${itemIndex}">
                    <div class="flex justify-between items-start mb-4">
                        <h4 class="text-md font-medium">Language ${itemIndex + 1}${itemIndex === 0 ? ' (Primary)' : ''}</h4>
                        ${itemIndex === 0 ? '' : `<button type="button" class="remove-btn" onclick="removeDynamicItem('${section}', ${itemIndex})">Remove</button>`}
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Language Name</label>
                            <div class="searchable-dropdown-container">
                                <input type="text" 
                                       class="language-dropdown language-search" 
                                       placeholder="Search and select language..." 
                                       autocomplete="off"
                                       data-index="${itemIndex}"
                                       value="${defaultLanguage}"
                                       onkeyup="filterLanguages(this)"
                                       onclick="toggleLanguageDropdown(this)"
                                       onblur="hideLanguageDropdown(this)"
                                       ${itemIndex === 0 ? 'readonly' : ''}>
                                <input type="hidden" name="${section}[${itemIndex}][language_name]" value="${defaultLanguage}" required>
                                <div class="language-options-dropdown" id="languageOptions_${itemIndex}" style="display: none;">
                                    ${motherTongues.map(language => 
                                        `<div class="language-option" onclick="selectLanguage('${language}', ${itemIndex})">${language}</div>`
                                    ).join('')}
                                </div>
                            </div>
                            ${itemIndex === 0 ? '<small class="text-gray-500 text-sm mt-1">English is set as your primary language</small>' : ''}
                        </div>
                        ${showProficiency ? `
                        <div class="form-group">
                            <label class="required">Proficiency Level</label>
                            <select name="${section}[${itemIndex}][proficiency_level]" required>
                                <option value="">Select Level</option>
                                <option value="Basic" ${data.proficiency_level === 'Basic' ? 'selected' : ''}>Basic</option>
                                <option value="Intermediate" ${data.proficiency_level === 'Intermediate' ? 'selected' : ''}>Intermediate</option>
                                <option value="Advanced" ${data.proficiency_level === 'Advanced' ? 'selected' : ''}>Advanced</option>
                                <option value="Native" ${data.proficiency_level === 'Native' ? 'selected' : ''}>Native</option>
                            </select>
                        </div>
                        ` : `
                        <input type="hidden" name="${section}[${itemIndex}][proficiency_level]" value="Known">
                        `}
                    </div>
                </div>
            `;
            break;
        case 'hobbies':
            itemHTML = `
                <div class="dynamic-item" data-index="${itemIndex}">
                    <div class="flex justify-between items-start mb-4">
                        <h4 class="text-md font-medium">Hobby ${itemIndex + 1}</h4>
                        <button type="button" class="remove-btn" onclick="removeDynamicItem('${section}', ${itemIndex})">Remove</button>
                    </div>
                    <div class="form-group">
                        <label class="required">Hobby Name</label>
                        <input type="text" name="${section}[${itemIndex}][hobby_name]" value="${data.hobby_name || ''}" required>
                    </div>
                </div>
            `;
            break;
        case 'references':
            itemHTML = `
                <div class="dynamic-item" data-index="${itemIndex}">
                    <div class="flex justify-between items-start mb-4">
                        <h4 class="text-md font-medium">Reference ${itemIndex + 1}</h4>
                        <button type="button" class="remove-btn" onclick="removeDynamicItem('${section}', ${itemIndex})">Remove</button>
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label class="required">Reference Name</label>
                            <input type="text" name="${section}[${itemIndex}][reference_name]" value="${data.reference_name || ''}" required>
                        </div>
                        <div class="form-group">
                            <label class="required">Relation</label>
                            <input type="text" name="${section}[${itemIndex}][relation]" value="${data.relation || ''}" required>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="required">Contact Information</label>
                        <input type="text" name="${section}[${itemIndex}][contact_info]" value="${data.contact_info || ''}" required>
                    </div>
                </div>
            `;
            break;
    }
    container.insertAdjacentHTML('beforeend', itemHTML);
    
    // Initialize character counters for new textarea fields
    const newItem = container.lastElementChild;
    const textareas = newItem.querySelectorAll('textarea.character-limit-textarea');
    textareas.forEach(textarea => {
        addCharacterLimitToTextarea(textarea, 250);
    });
    
    // Re-initialize lucide icons for the new item
    if (window.lucide && typeof window.lucide.createIcons === 'function') {
        window.lucide.createIcons();
    }
}
// Helper function to format date for input
function formatDateForInput(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toISOString().split('T')[0];
}
// Function to remove dynamic items
function removeDynamicItem(section, index) {
    const container = document.getElementById(`${section}List`);
    if (!container) return;
    // Prevent removal of 10th grade (first education entry)
    if (section === 'education' && index === 0) {
        showNotification('10th Grade education is required and cannot be removed.', 'warning');
        return;
    }
    const item = container.querySelector(`[data-index="${index}"]`);
    if (item) {
        // Additional check for required education entries
        if (item.classList.contains('required-education')) {
            showNotification('This education entry is required and cannot be removed.', 'warning');
            return;
        }
        
        item.remove();
        // Reindex remaining items
        reindexDynamicItems(section);
        
        // Special handling for experience section - show optional state if no items left
        if (section === 'experience') {
            checkExperienceOptionalState();
        }
        
        // Special handling for achievements section - show optional state if no items left
        if (section === 'achievements') {
            checkAchievementOptionalState();
        }
    }
}
// Function to reindex dynamic items
function reindexDynamicItems(section) {
    const container = document.getElementById(`${section}List`);
    if (!container) return;
    const items = container.querySelectorAll('.dynamic-item');
    items.forEach((item, newIndex) => {
        item.setAttribute('data-index', newIndex);
        
        // Update header
        const header = item.querySelector('h4');
        if (header) {
            header.textContent = `${section.charAt(0).toUpperCase() + section.slice(1).slice(0, -1)} ${newIndex + 1}`;
        }
        
        // Update input names
        const inputs = item.querySelectorAll('input, select, textarea');
        inputs.forEach(input => {
            const name = input.getAttribute('name');
            if (name) {
                const newName = name.replace(/\[\d+\]/, `[${newIndex}]`);
                input.setAttribute('name', newName);
            }
        });
        
        // Update remove button onclick
        const removeBtn = item.querySelector('.remove-btn');
        if (removeBtn) {
            removeBtn.setAttribute('onclick', `removeDynamicItem('${section}', ${newIndex})`);
        }
    });
}
// Function to toggle end date field
function toggleEndDate(checkbox, index) {
    const endDateInput = checkbox.closest('.dynamic-item').querySelector('input[name*="[end_date]"]');
    if (endDateInput) {
        endDateInput.disabled = checkbox.checked;
        if (checkbox.checked) {
            endDateInput.value = '';
        }
    }
}
// Function to add first experience (called from optional indicator button)
function addFirstExperience() {
    hideExperienceOptionalIndicator();
    addDynamicItem('experience');
}
// Function to check and show/hide experience optional state
function checkExperienceOptionalState() {
    const experienceList = document.getElementById('experienceList');
    const optionalIndicator = document.getElementById('experienceOptionalIndicator');
    
    if (experienceList && optionalIndicator) {
        const hasExperiences = experienceList.children.length > 0;
        
        if (hasExperiences) {
            optionalIndicator.style.display = 'none';
        } else {
            optionalIndicator.style.display = 'block';
        }
    }
}
// Function to hide experience optional indicator
function hideExperienceOptionalIndicator() {
    const optionalIndicator = document.getElementById('experienceOptionalIndicator');
    if (optionalIndicator) {
        optionalIndicator.style.display = 'none';
    }
}
// Function to show experience optional indicator
function showExperienceOptionalIndicator() {
    const optionalIndicator = document.getElementById('experienceOptionalIndicator');
    if (optionalIndicator) {
        optionalIndicator.style.display = 'block';
    }
}
// Achievement optional functions
function addFirstAchievement() {
    hideAchievementOptionalIndicator();
    addDynamicItem('achievements');
}
function checkAchievementOptionalState() {
    const achievementsList = document.getElementById('achievementsList');
    const optionalIndicator = document.getElementById('achievementOptionalIndicator');
    
    if (achievementsList && optionalIndicator) {
        const hasAchievements = achievementsList.children.length > 0;
        
        if (hasAchievements) {
            optionalIndicator.style.display = 'none';
        } else {
            optionalIndicator.style.display = 'block';
        }
    }
}
function hideAchievementOptionalIndicator() {
    const optionalIndicator = document.getElementById('achievementOptionalIndicator');
    if (optionalIndicator) {
        optionalIndicator.style.display = 'none';
    }
}
function showAchievementOptionalIndicator() {
    const optionalIndicator = document.getElementById('achievementOptionalIndicator');
    if (optionalIndicator) {
        optionalIndicator.style.display = 'block';
    }
}
// Function to collect form data
function collectFormData() {
    const formData = {};
    
    // Collect personal details
    const personalForm = document.getElementById('personalForm');
    if (personalForm) {
        const formDataObj = new FormData(personalForm);
        console.log('Personal form found, collecting data...');
        for (let [key, value] of formDataObj.entries()) {
            // Skip the phone field as we'll handle it separately
            if (key !== 'phone') {
                formData[key] = value;
                console.log(`Collected field: ${key} = ${value}`);
            }
        }
        
        // Handle phone number with country code
        const countryCode = document.getElementById('countryCode')?.value;
        const phoneNumber = document.getElementById('phone')?.value;
        if (countryCode && phoneNumber) {
            // Only include the fields that the backend expects
            formData.countryCode = countryCode;
            formData.mobile = phoneNumber; // Use 'mobile' instead of 'phone' to match backend
        }
    }
    
    // Collect dynamic sections
    const sections = ['education', 'skills', 'experience', 'projects', 'certifications', 'achievements', 'languages', 'hobbies', 'references'];
    sections.forEach(section => {
        formData[section] = [];
        const container = document.getElementById(`${section}List`);
        if (container) {
            const items = container.querySelectorAll('.dynamic-item');
            items.forEach((item, index) => {
                const itemData = {};
                const inputs = item.querySelectorAll('input, select, textarea');
                inputs.forEach(input => {
                    const name = input.getAttribute('name');
                    if (name) {
                        const fieldName = name.match(/\[([^\]]+)\]$/)?.[1];
                        if (fieldName) {
                            // Skip file input fields
                            if (input.type === 'file') {
                                return;
                            }
                            
                            if (input.type === 'checkbox') {
                                itemData[fieldName] = input.checked;
                            } else {
                                itemData[fieldName] = input.value;
                            }
                        }
                    }
                });
                if (Object.keys(itemData).length > 0) {
                    formData[section].push(itemData);
                }
            });
        }
    });
    
    return formData;
}
// Function to validate required fields
function validateForm() {
    const requiredFields = document.querySelectorAll('input[required], textarea[required], select[required]');
    let isValid = true;
    let missingFields = [];
    
    requiredFields.forEach(field => {
        // Skip disabled fields
        if (field.disabled) {
            return;
        }
        
        const fieldValue = field.value ? field.value.trim() : '';
        const fieldName = field.name || field.id || 'Unknown field';
        
        if (!fieldValue) {
            field.style.borderColor = '#ef4444';
            missingFields.push(fieldName);
            isValid = false;
            console.log(`Missing required field: ${fieldName}`);
        } else {
            field.style.borderColor = '#d1d5db';
        }
    });
    
    // Validate profile photo (mandatory)
    const profilePhotoPreview = document.getElementById('profilePhotoPreview');
    const profilePhotoMessage = document.getElementById('profilePhotoRequiredMessage');
    
    if (!profilePhotoFile || profilePhotoFile === 'existing') {
        if (profilePhotoPreview) {
            profilePhotoPreview.classList.add('required-missing');
        }
        if (profilePhotoMessage) {
            profilePhotoMessage.style.display = 'block';
        }
        missingFields.push('Profile Photo');
        isValid = false;
        console.log('Missing required field: Profile Photo');
    } else {
        if (profilePhotoPreview) {
            profilePhotoPreview.classList.remove('required-missing');
        }
        if (profilePhotoMessage) {
            profilePhotoMessage.style.display = 'none';
        }
    }
    
    // Log missing fields for debugging
    if (missingFields.length > 0) {
        console.log('Missing required fields:', missingFields);
        console.log('Total missing fields count:', missingFields.length);
    } else {
        console.log('All required fields are filled');
    }
    
    return isValid;
}
// Function to save profile to backend
function saveProfileToBackend() {
    if (!validateForm()) {
        showNotification('Please fill all required fields', 'warning');
        return;
    }
    const saveBtn = document.getElementById('saveProfileBtn');
    saveBtn.disabled = true;
    saveBtn.textContent = 'Saving...';
    
    // Create FormData object for multipart upload
    const formData = new FormData();
    
    // Collect form data
    const profileData = collectFormData();
    
    // IMPORTANT: Remove any userId field that might have been accidentally included
    // The backend expects userId in the URL path, not in the request body
    if (profileData.userId) {
        delete profileData.userId;
    }
    
    // Also remove any other fields that might cause issues
    if (profileData.id) {
        delete profileData.id; // Remove id field as well to be safe
    }
    
    // Create a Blob for the profile data with proper content type
    const profileBlob = new Blob([JSON.stringify(profileData)], {
        type: 'application/json'
    });
    formData.append('profile', profileBlob);
    
    // Append profile photo if available (new upload)
    if (profilePhotoFile && profilePhotoFile !== 'existing') {
        formData.append('profilePhoto', profilePhotoFile);
    }
    
    // Append signature if available (new upload)
    if (signatureFile && signatureFile !== 'existing') {
        formData.append('signature', signatureFile);
    }
    
    // Append certificate files
    const certifications = profileData.certifications || [];
    certifications.forEach((cert, index) => {
        const certFileInput = document.getElementById(`certFile_${index}`);
        if (certFileInput && certFileInput.files[0]) {
            formData.append('certificates', certFileInput.files[0]);
        }
    });
    
    console.log('Sending form data as multipart/form-data');
    console.log('Profile data:', profileData);
    
    // Use fetch with proper headers
    fetch(`${API_BASE_URL}/api/user/profile/${userId}`, {
        method: 'PUT',
        body: formData
        // Don't set Content-Type header, browser will set it automatically with boundary
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(err => {
                throw new Error(err.message || 'Network response was not ok');
            });
        }
        return response.json();
    })
    .then(data => {
        console.log('Profile saved successfully:', data);
        showNotification('Profile saved successfully!', 'success');
        toggleEditMode(false);
        // Refresh profile data
        currentUser = data.profile;
        renderProfile(data.profile);
    })
    .catch(error => {
        console.error('Error saving profile:', error);
        
        // Provide more specific error message for database size issues
        if (error.message.includes('Data too long')) {
            showNotification('The image file is too large. Please try a smaller image (under 1MB).', 'error');
        } else {
            showNotification(`Error saving profile: ${error.message}`, 'error');
        }
    })
    .finally(() => {
        saveBtn.disabled = false;
        saveBtn.textContent = 'Save Profile';
    });
}
// Image Upload Functions
function handleImageUpload(input, type) {
    const file = input.files[0];
    if (!file) return;
    
    // Validate file size (5MB max)
    const maxSize = 5 * 1024 * 1024; // 5MB in bytes
    if (file.size > maxSize) {
        showNotification('File size must be less than 5MB', 'warning');
        input.value = '';
        return;
    }
    
    // Validate file type
    const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif'];
    if (!allowedTypes.includes(file.type)) {
        showNotification('Please select a valid image file (JPG, PNG, GIF)', 'warning');
        input.value = '';
        return;
    }
    
    // Create preview URL
    const imageUrl = URL.createObjectURL(file);
    updateImagePreview(type, imageUrl, file.name);
    
    // Store file reference
    if (type === 'profilePhoto') {
        profilePhotoFile = file;
    } else if (type === 'signature') {
        signatureFile = file;
    }
}
function updateImagePreview(type, src, fileName) {
    const placeholder = document.getElementById(`${type}Placeholder`);
    const display = document.getElementById(`${type}Display`);
    const img = document.getElementById(`${type}Img`);
    const removeContainer = document.getElementById(`${type}RemoveContainer`);
    
    if (placeholder && display && img) {
        placeholder.style.display = 'none';
        display.style.display = 'block';
        img.src = src;
        img.alt = fileName;
        
        // Show the remove button container
        if (removeContainer) {
            removeContainer.style.display = 'block';
        }
        
        // Clear error state for profile photo
        if (type === 'profilePhoto') {
            const profilePhotoPreview = document.getElementById('profilePhotoPreview');
            const profilePhotoMessage = document.getElementById('profilePhotoRequiredMessage');
            
            if (profilePhotoPreview) {
                profilePhotoPreview.classList.remove('required-missing');
            }
            if (profilePhotoMessage) {
                profilePhotoMessage.style.display = 'none';
            }
        }
    }
}
function removeImage(type) {
    // Clear the file input
    const input = document.getElementById(`${type}Input`);
    if (input) {
        input.value = '';
    }
    
    // Reset preview
    const placeholder = document.getElementById(`${type}Placeholder`);
    const display = document.getElementById(`${type}Display`);
    const img = document.getElementById(`${type}Img`);
    const removeContainer = document.getElementById(`${type}RemoveContainer`);
    
    if (placeholder && display && img) {
        placeholder.style.display = 'flex';
        display.style.display = 'none';
        img.src = '';
        img.alt = '';
        
        // Hide the remove button container
        if (removeContainer) {
            removeContainer.style.display = 'none';
        }
        
        // Show error state for profile photo
        if (type === 'profilePhoto') {
            const profilePhotoPreview = document.getElementById('profilePhotoPreview');
            const profilePhotoMessage = document.getElementById('profilePhotoRequiredMessage');
            
            if (profilePhotoPreview) {
                profilePhotoPreview.classList.add('required-missing');
            }
            if (profilePhotoMessage) {
                profilePhotoMessage.style.display = 'block';
            }
        }
    }
    
    // Clear file reference
    if (type === 'profilePhoto') {
        profilePhotoFile = null;
    } else if (type === 'signature') {
        signatureFile = null;
    }
}
// Certificate File Upload Functions
function handleCertificationFileUpload(input, itemIndex) {
    const file = input.files[0];
    if (!file) return;
    
    // Validate file size (5MB max)
    const maxSize = 5 * 1024 * 1024; // 5MB in bytes
    if (file.size > maxSize) {
        showNotification('File size must be less than 5MB', 'warning');
        input.value = '';
        return;
    }
    
    // Validate file type
    const allowedTypes = ['application/pdf', 'image/jpeg', 'image/jpg', 'image/png'];
    if (!allowedTypes.includes(file.type)) {
        showNotification('Please select a valid file (PDF, JPG, PNG)', 'warning');
        input.value = '';
        return;
    }
    
    // Update certificate upload UI
    updateCertificationFileUI(itemIndex, file.name);
    
    // Mark the upload container as uploaded
    const uploadContainer = input.closest('.certification-file-upload');
    if (uploadContainer) {
        uploadContainer.classList.add('uploaded');
    }
}
function updateCertificationFileUI(itemIndex, fileName) {
    const placeholder = document.getElementById(`certUploadPlaceholder_${itemIndex}`);
    const fileInfo = document.getElementById(`certFileInfo_${itemIndex}`);
    const removeContainer = document.getElementById(`certRemoveContainer_${itemIndex}`);
    
    if (placeholder && fileInfo) {
        placeholder.style.display = 'none';
        fileInfo.style.display = 'block';
        
        // Update file info text
        const fileInfoText = fileInfo.querySelector('.file-info-text');
        if (fileInfoText) {
            fileInfoText.textContent = fileName;
        }
        
        // Show the remove button container
        if (removeContainer) {
            removeContainer.style.display = 'block';
        }
    }
}
function removeCertificationFile(itemIndex) {
    // Clear the file input
    const input = document.getElementById(`certFile_${itemIndex}`);
    if (input) {
        input.value = '';
    }
    
    // Reset UI
    const placeholder = document.getElementById(`certUploadPlaceholder_${itemIndex}`);
    const fileInfo = document.getElementById(`certFileInfo_${itemIndex}`);
    const removeContainer = document.getElementById(`certRemoveContainer_${itemIndex}`);
    const uploadContainer = input ? input.closest('.certification-file-upload') : null;
    
    if (placeholder && fileInfo) {
        placeholder.style.display = 'flex';
        fileInfo.style.display = 'none';
    }
    
    // Hide the remove button container
    if (removeContainer) {
        removeContainer.style.display = 'none';
    }
    
    if (uploadContainer) {
        uploadContainer.classList.remove('uploaded');
    }
}
function formatFileSize(bytes) {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}
// Function to check login status from both sessionStorage and localStorage
function checkLoginStatus() {
    // Check sessionStorage first (non-persistent login)
    let loggedInUser = sessionStorage.getItem('loggedInUser');
    
    // If not found in sessionStorage, check localStorage (persistent login)
    if (!loggedInUser) {
        const persistentLogin = localStorage.getItem('persistentLogin');
        if (persistentLogin) {
            try {
                const persistentData = JSON.parse(persistentLogin);
                
                // Check if the persistent login has expired
                if (persistentData.expiresAt && new Date(persistentData.expiresAt) > new Date()) {
                    // If not expired, use the persistent login data
                    sessionStorage.setItem('loggedInUser', JSON.stringify(persistentData));
                    loggedInUser = persistentData;
                } else {
                    // If expired, remove the persistent login data
                    localStorage.removeItem('persistentLogin');
                    console.log('Removed expired persistent login data');
                }
            } catch (e) {
                console.error('Error parsing persistent login data:', e);
                localStorage.removeItem('persistentLogin');
            }
        }
    }
    
    return loggedInUser;
}
// Fetch profile from backend
function fetchProfileFromBackend() {
    if (!userId) {
        console.error('User ID not available for fetching profile');
        return;
    }
    
    console.log(`Fetching profile for user ID: ${userId}`);
    
    fetch(`${API_BASE_URL}/api/user/profile/${userId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch profile');
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                console.log('Profile data received:', data.profile);
                currentUser = data.profile;
                renderProfile(data.profile);
            } else {
                console.error('Error fetching profile:', data.message);
                showNotification(`Error fetching profile: ${data.message}`, 'error');
                
                // If unauthorized, redirect to login
                if (response.status === 401) {
                    sessionStorage.removeItem('loggedInUser');
                    localStorage.removeItem('persistentLogin');
                    window.location.href = "login.html";
                }
            }
        })
        .catch(error => {
            console.error('Error fetching profile:', error);
            showNotification('Error fetching profile. Please check your connection and try again.', 'error');
        });
}
// Initialize profile page
document.addEventListener('DOMContentLoaded', function() {
    console.log('Profile page DOM loaded');
    
    // Check if user is logged in using the enhanced checkLoginStatus function
    const loggedInUser = checkLoginStatus();
    if (!loggedInUser) {
        console.log('No logged-in user found, redirecting to login');
        window.location.href = "login.html";
        return;
    }
    
    // Parse user data
    try {
        currentUser = JSON.parse(loggedInUser);
        userId = currentUser.id;
        console.log('User logged in:', currentUser);
    } catch (e) {
        console.error('Error parsing logged in user data:', e);
        // Clear invalid data
        sessionStorage.removeItem('loggedInUser');
        localStorage.removeItem('persistentLogin');
        window.location.href = "login.html";
        return;
    }
    
    // Render profile from backend
    fetchProfileFromBackend();
    
    // Initialize character counter for Professional Objective field
    const objectiveField = document.getElementById('objective');
    if (objectiveField) {
        addCharacterLimitToTextarea(objectiveField, 250);
    }
    
    // Initialize DOB validation
    setDOBMaxDate();
    
    // Initialize phone validation
    setupPhoneValidation();
    
    // Initialize education section with 10th grade if empty
    setTimeout(() => {
        const educationContainer = document.getElementById('educationList');
        if (educationContainer && educationContainer.children.length === 0) {
            addDynamicItem('education', {
                institution_name: '',
                degree: '10th Grade (Secondary)',
                branch: 'General',
                start_year: '',
                end_year: '',
                grade: ''
            }, 0);
        }
    }, 500);
    
    // Edit profile button
    const editBtn = document.getElementById('editProfileBtn');
    if (editBtn) {
        editBtn.addEventListener('click', () => toggleEditMode(true));
    }
    
    // Cancel edit button
    const cancelBtn = document.getElementById('cancelEditBtn');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', () => toggleEditMode(false));
    }
    
    // Save profile button
    const saveBtn = document.getElementById('saveProfileBtn');
    if (saveBtn) {
        saveBtn.addEventListener('click', saveProfileToBackend);
    }
    
    // Tab navigation
    const tabBtns = document.querySelectorAll('.tab-btn');
    tabBtns.forEach(btn => {
        btn.addEventListener('click', function() {
            const tabName = this.getAttribute('data-tab');
            switchTab(tabName);
        });
    });
    
    // Navigation buttons
    const prevBtn = document.getElementById('prevTabBtn');
    if (prevBtn) {
        prevBtn.addEventListener('click', function() {
            if (currentTabIndex > 0) {
                switchTab(tabs[currentTabIndex - 1]);
            }
        });
    }
    
    const nextBtn = document.getElementById('nextTabBtn');
    if (nextBtn) {
        nextBtn.addEventListener('click', function() {
            if (currentTabIndex < tabs.length - 1) {
                switchTab(tabs[currentTabIndex + 1]);
            }
        });
    }
    
    // Add dynamic item buttons
    const addButtons = [
        { id: 'addEducationBtn', section: 'education' },
        { id: 'addSkillBtn', section: 'skills' },
        { id: 'addExperienceBtn', section: 'experience' },
        { id: 'addProjectBtn', section: 'projects' },
        { id: 'addCertificationBtn', section: 'certifications' },
        { id: 'addAchievementBtn', section: 'achievements' },
        { id: 'addLanguageBtn', section: 'languages' },
        { id: 'addHobbyBtn', section: 'hobbies' },
        { id: 'addReferenceBtn', section: 'references' }
    ];
    
    addButtons.forEach(({ id, section }) => {
        const btn = document.getElementById(id);
        if (btn) {
            btn.addEventListener('click', function(e) {
                e.preventDefault();
                
                // Special handling for experience section
                if (section === 'experience') {
                    hideExperienceOptionalIndicator();
                }
                
                // Special handling for achievements section
                if (section === 'achievements') {
                    hideAchievementOptionalIndicator();
                }
                
                addDynamicItem(section);
            });
        }
    });
    
    // Initialize navigation buttons
    updateNavigationButtons();
    
    // Initialize experience optional state
    checkExperienceOptionalState();
    
    // Initialize achievements optional state
    checkAchievementOptionalState();
    
    // Setup notification buttons and logout button after a short delay
    setTimeout(() => {
        setupNotificationButtons();
        setupLogoutButton();
        setupLogoutModal(); // Setup logout modal event listeners
        fetchNotificationCounts();
    }, 1000);
    
    // Set up event listener for sidebar loaded event
    window.addEventListener('sidebarLoaded', function() {
        console.log('Sidebar loaded, updating notification count');
        fetchNotificationCounts();
    });
    
    // We'll also try to update the notification badges after a delay in case the event was already fired
    setTimeout(() => {
        fetchNotificationCounts();
    }, 2000); // Try after 2 seconds as a fallback
    
    // Set up periodic refresh of notification count (every 5 minutes)
    setInterval(fetchNotificationCounts, 300000);
});
// Make fetchNotificationCounts globally available for other scripts to call
window.fetchNotificationCounts = fetchNotificationCounts;
// Sidebar Integration
class PageManager {
    constructor() {
        this.loadSidebar();
    }
    loadSidebar() {
        fetch('sidebar.html')
            .then(response => response.text())
            .then(html => {
                const parser = new DOMParser();
                const doc = parser.parseFromString(html, 'text/html');
                
                const overlay = doc.getElementById('sidebar-overlay');
                const sidebar = doc.getElementById('sidebar');
                const header = doc.getElementById('top-header');
                const mobileHeader = doc.getElementById('mobile-header');
                const style = doc.querySelector('style');
                
                if (style) {
                    document.head.appendChild(style);
                }
                
                const container = document.getElementById('sidebar-container');
                if (overlay) container.appendChild(overlay);
                if (sidebar) container.appendChild(sidebar);
                
                if (header) {
                    document.body.insertBefore(header, document.getElementById('main-content'));
                }
                if (mobileHeader) {
                    document.body.insertBefore(mobileHeader, document.getElementById('main-content'));
                }
                
                this.initializeSidebarFunctionality();
                lucide.createIcons();
                
                // Dispatch sidebarLoaded event
                window.dispatchEvent(new CustomEvent('sidebarLoaded'));
                
                setTimeout(() => {
                    this.updateWelcomeMessage();
                    // Fetch notification count after sidebar is loaded
                    this.fetchNotificationCount();
                }, 100);
            })
            .catch(error => console.error('Error loading sidebar:', error));
    }
    initializeSidebarFunctionality() {
        // Initialize sidebar
        class SidebarManager {
            constructor() {
                this.sidebar = document.getElementById('sidebar');
                this.sidebarToggle = document.getElementById('sidebar-toggle');
                this.sidebarOverlay = document.getElementById('sidebar-overlay');
                this.logoutBtn = document.getElementById('logout-btn');
                this.mobileSidebarToggle = document.getElementById('mobile-sidebar-toggle');
                this.mobileNotificationBtn = document.getElementById('mobile-notification-btn');
                this.notificationBtn = document.getElementById('notification-btn');
                this.isCollapsed = false;
                this.isMobile = window.innerWidth < 768;
                
                this.init();
            }
            init() {
                // Set initial state based on screen size
                this.handleResize();
                
                // Event listeners
                this.sidebarToggle.addEventListener('click', () => this.toggleSidebar());
                this.sidebarOverlay.addEventListener('click', () => this.closeMobileSidebar());
                this.logoutBtn.addEventListener('click', () => this.showLogoutModal());
                
                // Mobile header event listeners
                if (this.mobileSidebarToggle) {
                    this.mobileSidebarToggle.addEventListener('click', () => this.toggleSidebar());
                }
                if (this.mobileNotificationBtn) {
                    this.mobileNotificationBtn.addEventListener('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        this.handleMobileNotification();
                    });
                }
                if (this.notificationBtn) {
                    this.notificationBtn.addEventListener('click', (e) => {
                        e.preventDefault();
                        e.stopPropagation();
                        this.handleMobileNotification();
                    });
                }
                
                // Window resize handler
                window.addEventListener('resize', () => this.handleResize());
                
                // Set active page
                this.setActivePage();
                
                // Update current date
                this.updateCurrentDate();
                
                // Initialize icons
                lucide.createIcons();
                
                // Set initial main content state
                setTimeout(() => {
                    this.notifyMainContent();
                }, 100);
                
                // Setup logout modal event listeners
                this.setupLogoutModal();
            }
            
            setupLogoutModal() {
                const logoutModal = document.getElementById('logout-modal');
                const logoutCancelBtn = document.getElementById('logout-cancel-btn');
                const logoutConfirmBtn = document.getElementById('logout-confirm-btn');
                
                // Cancel button - just close the modal
                logoutCancelBtn.addEventListener('click', () => {
                    logoutModal.style.display = 'none';
                });
                
                // Confirm button - perform logout
                logoutConfirmBtn.addEventListener('click', () => {
                    this.performLogout();
                });
                
                // Close modal when clicking outside
                logoutModal.addEventListener('click', (e) => {
                    if (e.target === logoutModal) {
                        logoutModal.style.display = 'none';
                    }
                });
            }
            
            showLogoutModal() {
                const logoutModal = document.getElementById('logout-modal');
                logoutModal.style.display = 'flex';
            }
            
            performLogout() {
                // Clear all session data
                localStorage.clear();
                sessionStorage.clear();
                
                // Redirect to login page
                window.location.href = 'login.html';
            }
            
            fetchNotificationCount() {
                // Use global fetchNotificationCounts function
                if (typeof window.fetchNotificationCounts === 'function') {
                    window.fetchNotificationCounts()
                        .then(count => {
                            console.log('Notification count fetched:', count);
                        })
                        .catch(error => {
                            console.error('Error fetching notification count:', error);
                        });
                }
            }
            updateNotificationBadge(count) {
                // Use global updateNotificationBadge function
                if (typeof window.updateNotificationBadge === 'function') {
                    window.updateNotificationBadge(count);
                }
            }
            toggleSidebar() {
                if (this.isMobile) {
                    this.toggleMobileSidebar();
                } else {
                    this.toggleDesktopSidebar();
                }
            }
            toggleDesktopSidebar() {
                this.isCollapsed = !this.isCollapsed;
                
                if (this.isCollapsed) {
                    this.sidebar.classList.remove('sidebar-expanded');
                    this.sidebar.classList.add('sidebar-collapsed');
                } else {
                    this.sidebar.classList.remove('sidebar-collapsed');
                    this.sidebar.classList.add('sidebar-expanded');
                }
                
                this.notifyMainContent();
                
                localStorage.setItem('sidebarCollapsed', this.isCollapsed);
            }
            toggleMobileSidebar() {
                const isOpen = this.sidebar.classList.contains('mobile-open');
                
                if (isOpen) {
                    this.closeMobileSidebar();
                } else {
                    this.openMobileSidebar();
                }
            }
            openMobileSidebar() {
                this.sidebar.classList.add('mobile-open');
                this.sidebarOverlay.classList.remove('hidden');
                document.body.style.overflow = 'hidden';
            }
            closeMobileSidebar() {
                this.sidebar.classList.remove('mobile-open');
                this.sidebarOverlay.classList.add('hidden');
                document.body.style.overflow = '';
            }
            handleResize() {
                const wasMobile = this.isMobile;
                this.isMobile = window.innerWidth < 768;
                
                if (wasMobile !== this.isMobile) {
                    if (this.isMobile) {
                        this.closeMobileSidebar();
                        this.sidebar.classList.remove('sidebar-collapsed', 'sidebar-expanded');
                    } else {
                        this.sidebar.classList.remove('mobile-open');
                        this.sidebarOverlay.classList.add('hidden');
                        document.body.style.overflow = '';
                        
                        const savedState = localStorage.getItem('sidebarCollapsed');
                        this.isCollapsed = savedState === 'true';
                        
                        if (this.isCollapsed) {
                            this.sidebar.classList.remove('sidebar-expanded');
                            this.sidebar.classList.add('sidebar-collapsed');
                        } else {
                            this.sidebar.classList.remove('sidebar-collapsed');
                            this.sidebar.classList.add('sidebar-expanded');
                        }
                    }
                    
                    this.notifyMainContent();
                } else if (!this.isMobile) {
                    const savedState = localStorage.getItem('sidebarCollapsed');
                    this.isCollapsed = savedState === 'true';
                    
                    if (this.isCollapsed) {
                        this.sidebar.classList.remove('sidebar-expanded');
                        this.sidebar.classList.add('sidebar-collapsed');
                    } else {
                        this.sidebar.classList.remove('sidebar-collapsed');
                        this.sidebar.classList.add('sidebar-expanded');
                    }
                }
            }
            setActivePage() {
                const currentPage = window.location.pathname.split('/').pop() || 'profile.html';
                const pageMap = {
                    'dashboard.html': 'dashboard',
                    'profile.html': 'profile',
                    'resumes.html': 'resumes',
                    'notifications.html': 'notifications',
                    'help.html': 'help'
                };
                
                const activePage = pageMap[currentPage];
                if (activePage) {
                    const activeLink = document.querySelector(`[data-page="${activePage}"]`);
                    if (activeLink) {
                        activeLink.classList.add('active');
                    }
                }
            }
            notifyMainContent() {
                const header = document.getElementById('top-header');
                if (header) {
                    if (this.isMobile) {
                        header.classList.remove('sidebar-collapsed');
                    } else if (this.isCollapsed) {
                        header.classList.add('sidebar-collapsed');
                    } else {
                        header.classList.remove('sidebar-collapsed');
                    }
                }
                
                const event = new CustomEvent('sidebarStateChanged', {
                    detail: {
                        isCollapsed: this.isCollapsed,
                        isMobile: this.isMobile
                    }
                });
                window.dispatchEvent(event);
            }
            updateCurrentDate() {
                const dateElement = document.getElementById('current-date');
                if (dateElement) {
                    const now = new Date();
                    const options = { 
                        weekday: 'short',
                        year: 'numeric',
                        month: 'short',
                        day: 'numeric',
                    };
                    dateElement.textContent = now.toLocaleDateString('en-US', options);
                }
            }
            handleMobileNotification() {
                window.location.href = 'notifications.html';
            }
        }
        window.sidebarManager = new SidebarManager();
        window.SidebarManager = SidebarManager;
    }
    handleSidebarStateChanges() {
        window.addEventListener('sidebarStateChanged', (event) => {
            const { isCollapsed, isMobile } = event.detail;
            
            if (!isMobile) {
                this.mainContent.classList.add('sidebar-collapsed');
            } else {
                this.mainContent.classList.remove('sidebar-collapsed');
            }
        });
    }
    updateWelcomeMessage() {
        const welcomeMessage = document.getElementById('welcome-message');
        const headerSubtitle = document.getElementById('header-subtitle');
        
        if (welcomeMessage) {
            welcomeMessage.textContent = 'Profile Management';
        }
        if (headerSubtitle) {
            headerSubtitle.textContent = 'Manage your personal information and settings';
        }
        
        const currentDateElement = document.getElementById('current-date');
        if (currentDateElement) {
            const now = new Date();
            const options = { 
                weekday: 'long', 
                year: 'numeric', 
                month: 'long', 
                day: 'numeric' 
            };
            currentDateElement.textContent = now.toLocaleDateString('en-US', options);
        }
    }
    fetchNotificationCount() {
        // Use global fetchNotificationCounts function
        if (typeof window.fetchNotificationCounts === 'function') {
            window.fetchNotificationCounts()
                .then(count => {
                    console.log('Notification count fetched from PageManager:', count);
                })
                .catch(error => {
                    console.error('Error fetching notification count from PageManager:', error);
                });
        }
    }
}
document.addEventListener('DOMContentLoaded', () => {
    new PageManager();
    
    // Set up periodic refresh of notification count (every 5 minutes)
    setInterval(() => {
        if (typeof window.fetchNotificationCounts === 'function') {
            window.fetchNotificationCounts()
                .catch(error => {
                    console.error('Error in periodic notification count update:', error);
                });
        }
    }, 300000);
    
    // Initial update after a short delay to ensure everything is loaded
    setTimeout(() => {
        if (typeof window.fetchNotificationCounts === 'function') {
            window.fetchNotificationCounts()
                .catch(error => {
                    console.error('Error in initial notification count update:', error);
                });
        }
    }, 1000);
});
window.addEventListener('sidebarStateChanged', (event) => {
    const mainContent = document.getElementById('main-content');
    const { isCollapsed, isMobile } = event.detail;
    if (!mainContent) return;
    if (!isMobile) {
        if (isCollapsed) {
            mainContent.classList.add('sidebar-collapsed');
        } else {
            mainContent.classList.remove('sidebar-collapsed');
        }
    } else {
        mainContent.classList.remove('sidebar-collapsed');
    }
});
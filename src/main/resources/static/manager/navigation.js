// Navigation Component
class Navigation {
	constructor() {
		this.init();
	}

	init() {
		this.injectCSS();
		this.injectHTML();
		this.fetchAndDisplayProfile();
		this.startUnreadPolling();
	}

	injectCSS() {
	  const style = document.createElement('style');
	  style.textContent = `
	    /* Base Styles */
	    body {
	      font-family: 'Poppins', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	      background: #F9FAFB; /* Soft neutral background */
	      color: #1E293B; 
	      margin: 0;
	      padding: 0;
	      overflow: hidden;
	      line-height: 1.6;
	    }

	    .header {
	      position: fixed;
	      top: 0;
	      left: 240px;
	      width: calc(100% - 240px);
	      height: 64px;
	      background: linear-gradient(135deg, #1e3a8a, #1e40af);
	      color: #FFFFFF;
	      padding: 0 24px;
	      font-size: 22px;
	      font-weight: 600;
	      z-index: 1000;
	      display: flex;
	      align-items: center;
	      justify-content: space-between;
	      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
	    }

	    .header-title {
	      flex: 1;
	      letter-spacing: 0.5px;
	    }

	    .profile-section {
	      display: flex;
	      align-items: center;
	      gap: 14px;
	      cursor: pointer;
	      position: relative;
	    }

	    .profile-image {
	      width: 40px;
	      height: 40px;
	      border-radius: 50%;
	      border: 3px solid #FFFFFF;
	      object-fit: cover;
	      box-shadow: 0 3px 8px rgba(0, 0, 0, 0.2);
	    }

	    .profile-info {
	      display: flex;
	      flex-direction: column;
	      align-items: flex-end;
	    }

	    .profile-name {
	      font-size: 15px;
	      font-weight: 600;
	      margin: 0;
	      color: #FFFFFF;
	    }

	    .profile-email {
	      font-size: 13px;
	      color: #F1F5F9;
	      opacity: 0.9;
	      margin: 0;
	    }

	    .profile-dropdown {
	      display: none;
	      position: absolute;
	      top: 100%;
	      right: 0;
	      background-color: #FFFFFF;
	      color: #1E293B;
	      border: 1px solid #E2E8F0;
	      border-radius: 10px;
	      box-shadow: 0 8px 24px rgba(0, 0, 0, 0.2);
	      z-index: 1001;
	      min-width: 180px;
	      overflow: hidden;
	    }

	    .profile-dropdown.active {
	      display: block;
	    }

	    .dropdown-item {
	      display: block;
	      padding: 12px 16px;
	      text-decoration: none;
	      color: #1E293B;
	      font-size: 14px;
	      font-weight: 500;
	    }

	    .dropdown-item:hover {
	      background-color: #F1F5F9;
	      color: #3b82f6;
	    }

	    .dropdown-item.delete {
	      color: #EF4444;
	      font-weight: 600;
	    }

	    .dropdown-item.delete:hover {
	      background-color: #FEE2E2;
	      color: #B91C1C;
	    }

	    #profileUploadInput {
	      display: none;
	    }

	    .sidebar {
	      position: fixed;
	      top: 0;
	      left: 0;
	      width: 240px;
	      height: 100vh;
	      background: linear-gradient(180deg, #1e3a8a, #3b82f6);
	      color: #FFFFFF;
	      padding: 62px 16px;
	      overflow-y: auto;
	      box-shadow: 3px 0 12px rgba(0, 0, 0, 0.2);
	    }

	    .sidebar h2 {
	      text-align: center;
	      margin: 0 0 32px;
	      font-size: 24px;
	      font-weight: 700;
	      color: #F1F5F9;
	    }

	    .sidebar a {
	      display: flex;
	      align-items: center;
	      gap: 12px;
	      color: #D1D5DB;
	      text-decoration: none;
	      padding: 10px 16px;
	      margin: 20px 10px;
	      border-radius: 8px;
	      font-size: 15px;
	      font-weight: 500;
	      transition: all 0.3s ease;
	    }

	    .sidebar a i {
	      font-size: 16px;
	      width: 24px;
	      text-align: center;
	    }

	    /* KEEP ONLY sidebar hover + active animation */
	    .sidebar a:hover {
	      background: #3b82f6;
	      color: #FFFFFF;
	      transform: translateX(5px);
	      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
	    }

	    .sidebar a.active {
	      background: #3b82f6;
	      color: #FFFFFF;
	      font-weight: 600;
	      box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
	    }

	    .sidebar a[href="Logout.html"] {
	      background: #EF4444;
	      color: #FFFFFF;
	      font-weight: 600;
	    }

	    .sidebar a[href="Logout.html"]:hover {
	      background: #B91C1C;
	      color: #FFFFFF;
	      transform: translateX(5px);
	    }

	    .main {
	      position: absolute;
	      top: 64px;
	      left: 240px;
	      right: 0;
	      bottom: 0;
	      padding: 32px;
	      overflow-y: auto;
	      border-radius: 10px 0 0 0;
	      background: linear-gradient(135deg, #e0f2fe, #ede9fe);
	    }

	    .notification-badge {
	      background: red;
	      color: #FFFFFF;
	      font-size: 12px;
	      font-weight: 600;
	      border-radius: 50%;
	      padding: 4px 8px;
	      margin-left: 12px;
	      display: inline-block;
	      min-width: 22px;
	      text-align: center;
	      box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
	    }

	    /* Responsive */
	    @media (max-width: 768px) {
	      .sidebar {
	        width: 100%;
	        height: auto;
	        position: relative;
	        top: 0;
	        padding: 24px 16px;
	        z-index: 1001;
	      }

	      .header {
	        left: 0;
	        width: 100%;
	        padding: 16px 20px;
	        height: 56px;
	      }

	      .main {
	        left: 0;
	        top: 56px;
	        position: relative;
	        border-radius: 0;
	      }
	    }
	  `;
	  document.head.appendChild(style);
	}

	injectHTML() {
		const currentPage = window.location.pathname.split('/').pop();

		const headerHTML = `
      <div class="header">
        <div class="header-title" id="navigationHeader">Manager Dashboard</div>
        <div class="profile-section" id="profileSection">
          <img alt="Profile" class="profile-image" id="profileImage">
          <div class="profile-info">
            <div class="profile-name" id="profileName"></div>
            <div class="profile-email" id="profileEmail"></div>
          </div>
        </div>
      </div>
    `;

		const sidebarHTML = `
      <div class="sidebar">
        <h2>Manager Panel</h2>
        <a href="/manager/user.html" class="${currentPage === 'user.html' ? 'active' : ''}">
          <i class="fas fa-user"></i> Profile
        </a>
        <a href="/manager/Assignuser.html" class="${currentPage === 'Assignuser.html' ? 'active' : ''}">
          <i class="fas fa-tasks"></i> Assign Users
        </a>
        <a href="/manager/Tracking.html" class="${currentPage === 'Tracking.html' ? 'active' : ''}">
          <i class="fas fa-eye"></i> Tracking
        </a>
        <a href="/manager/AdminDetails.html" class="${currentPage === 'AdminDetails.html' ? 'active' : ''}">
          <i class="fas fa-users-cog"></i> Admin Details
        </a>
		<a href="/manager/Notification.html" class="${currentPage === 'Notification.html' ? 'active' : ''}">
		  <i class="fas fa-bell"></i> Notification 
		  <span id="notificationBadge" class="notification-badge" style="display:none;">0</span>
		</a>
        <a href="/manager/help.html" class="${currentPage === 'help.html' ? 'active' : ''}">
          <i class="fas fa-life-ring"></i> Help & Support
        </a>
        <a href="/manager/Logout.html" class="${currentPage === 'Logout.html' ? 'active' : ''}">
          <i class="fas fa-sign-out-alt"></i> Logout
        </a>
      </div>
    `;

		// Insert sidebar first
		document.body.insertAdjacentHTML('afterbegin', sidebarHTML);

		// Insert header and file input after sidebar
		document.body.insertAdjacentHTML('afterbegin', headerHTML);

		// Ensure main content area exists
		this.ensureMainContentArea();
	}

	ensureMainContentArea() {
		let mainContent = document.querySelector('.main, .main-content');
		if (!mainContent) {
			const bodyContent = Array.from(document.body.children).filter(child =>
				!child.classList.contains('header') && !child.classList.contains('sidebar')
			);

			if (bodyContent.length > 0) {
				const mainDiv = document.createElement('div');
				mainDiv.className = 'main';

				bodyContent.forEach(child => {
					mainDiv.appendChild(child);
				});

				document.body.appendChild(mainDiv);
			}
		}
	}
	fetchAndDisplayProfile() {
	    const email = localStorage.getItem('managerEmail');
	    if (!email) return;

	    const DEFAULT_AVATAR = "https://www.w3schools.com/w3images/avatar2.png";

	    fetch(`/api/manager/profile?email=${encodeURIComponent(email)}`)
	        .then(response => response.json())
	        .then(manager => {
	            if (manager) {
	                const name = manager.name || "Manager";
	                const email = manager.email || "";
	                const imageUrl =
	                    manager.imageBase64 && manager.imageBase64.trim() !== ""
	                        ? manager.imageBase64
	                        : DEFAULT_AVATAR;   // ✅ use local constant

	                this.setProfileInfo(name, email, imageUrl);
	            }
	        })
	        .catch(err => {
	            console.error("Failed to fetch manager profile:", err);
	            this.setProfileInfo("Manager", "", DEFAULT_AVATAR); // fallback
	        });
	}



	setHeaderTitle(title) {
		const header = document.getElementById('navigationHeader');
		if (header) {
			header.textContent = title;
		}
	}

	setProfileInfo(name, email, imageUrl = null) {
		const profileName = document.getElementById('profileName');
		const profileEmail = document.getElementById('profileEmail');
		const profileImage = document.getElementById('profileImage');

		if (profileName && name) {
			profileName.textContent = name;
		}

		if (profileEmail && email) {
			profileEmail.textContent = email;
		}

		if (profileImage && imageUrl) {
			profileImage.src = imageUrl;
		}
	}
	updateNotificationBadge(unreadCount) {
			const badge = document.getElementById("notificationBadge");
			if (!badge) return;

			if (unreadCount > 0) {
				badge.textContent = unreadCount;
				badge.style.display = "inline-block";
			} else {
				badge.style.display = "none"; // hide if no unread
			}
		}

		async fetchUnreadCount() {
			const email = localStorage.getItem("managerEmail");
			if (!email) return;

			try {
				const res = await fetch(`/api/manager/notifications/received?email=${encodeURIComponent(email)}`);
				if (!res.ok) throw new Error("Failed to fetch unread count");

				const received = await res.json();
				const unreadCount = received.filter(n => n.read === false).length;

				this.updateNotificationBadge(unreadCount);
			} catch (err) {
				console.error("Error fetching unread count:", err);
			}
		}

		startUnreadPolling() {
			this.fetchUnreadCount(); // First fetch
			setInterval(() => this.fetchUnreadCount(), 5000); // Refresh every 10s
		}
}



// Initialize navigation when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
	window.navigation = new Navigation();
});

// Export for use in other scripts
if (typeof module !== 'undefined' && module.exports) {
	module.exports = Navigation;
}
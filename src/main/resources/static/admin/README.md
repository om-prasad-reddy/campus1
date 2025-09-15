# Admin Dashboard - Refactored Structure

## Overview
The HTML files have been refactored to use a common header and navigation system for better maintainability.

## Files Structure

### Core Files
- `admin_profile.html` - Admin profile page
- `logout.html` - Logout confirmation page  
- `resume_users_data.html` - Main users data management page

### Common Components
- `common.js` - Shared JavaScript file containing header and navigation functionality
- `header_nav.html` - Template file (for reference, not used directly)

## How It Works

1. **Common Header & Navigation**: All three HTML files now include `common.js` which dynamically generates the header and sidebar navigation.

2. **Centralized Maintenance**: To modify the header or navigation:
   - Edit the `loadHeaderAndNav()` function in `common.js`
   - Changes will automatically apply to all pages

3. **Dynamic Date/Time**: The header displays current date and time in IST, updated every minute.

## Key Benefits

- **DRY Principle**: No duplicate header/navigation code across files
- **Easy Maintenance**: Single point of change for header/navigation updates
- **Consistent UI**: Ensures all pages have identical header and navigation
- **Real-time Updates**: Dynamic date/time display

## Technical Details

### JavaScript Functions in common.js:
- `loadHeaderAndNav()` - Generates and inserts header and sidebar HTML
- `updateDateTime()` - Updates the date/time display every minute

### CSS Styles:
- All header and navigation styles are preserved in each HTML file
- Responsive design maintained
- Consistent styling across all pages

## Login System

### Demo Credentials
The system includes a demo login page with the following test credentials:

- **Username:** `admin` | **Password:** `admin@2025`
- **Username:** `admin@campus1.com` | **Password:** `admin123`  
- **Username:** `superadmin` | **Password:** `super123`

### Authentication Flow
1. **Login Page** (`login.html`) - Entry point for the application
2. **Session Management** - Uses localStorage to maintain login state
3. **Protected Pages** - All admin pages check login status before loading
4. **Logout Process** - Clears session and redirects to login page

### Security Features
- Password visibility toggle
- Form validation
- Session state checking
- Automatic redirects for unauthorized access
- Remember me functionality

## Usage

1. **First Time Access:** Open `login.html` in a web browser
2. **Login:** Use any of the demo credentials above
3. **Navigation:** Access admin pages through the sidebar navigation
4. **Logout:** Use the logout page to end your session

## File Dependencies

Each HTML file depends on:
- `common.js` (required for header/navigation and login checking)
- Font Awesome CSS (for icons)
- Individual page-specific JavaScript (where applicable)
- Login state stored in browser's localStorage

## Maintenance Notes

- To add new navigation items: Edit the `sidebarHTML` variable in `common.js`
- To modify header content: Edit the `headerHTML` variable in `common.js`
- To add new login credentials: Edit the `validCredentials` object in `login.html`
- Page-specific functionality remains in individual HTML files
import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { useAuth } from "../../context/AuthContext";
import ThemeToggle from "./ThemeToggle";

function Header() {
  const { employee, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const [showUserMenu, setShowUserMenu] = useState(false);

  const initials = employee
    ? `${employee.firstName.charAt(0)}${employee.lastName.charAt(0)}`.toUpperCase()
    : "";

  const handleProfile = () => {
    setShowUserMenu(false);
    navigate("/profile");
  };

  const handleLogout = () => {
    setShowUserMenu(false);
    logout();
    navigate("/login", { replace: true });
  };

  return (
    <header className="flex h-16 items-center justify-between border-b border-gray-200 bg-white px-6 dark:border-gray-800 dark:bg-gray-900">
      {/* Page title */}
      <div>
        <h1 className="text-lg font-semibold text-gray-900 dark:text-white">
          Dashboard
        </h1>
      </div>

      {/* User section */}
      <div className="flex items-center gap-4">
        <ThemeToggle />

        {isAuthenticated && employee ? (
          <>
            <div className="text-right">
              <p className="text-sm font-semibold text-gray-900 dark:text-white">
                {employee.firstName} {employee.lastName}
              </p>

              <p className="text-xs text-gray-500 dark:text-gray-400">
                {employee.role}
              </p>
            </div>

            {/* User menu */}
            <div className="relative">
              <button
                type="button"
                onClick={() => setShowUserMenu((current) => !current)}
                className="flex h-10 w-10 items-center justify-center rounded-full bg-gray-200 text-sm font-semibold text-gray-700 transition-colors hover:bg-gray-300 dark:bg-gray-700 dark:text-white dark:hover:bg-gray-600"
              >
                {initials}
              </button>

              {showUserMenu && (
                <div className="absolute right-0 top-12 z-50 w-48 rounded-lg border border-gray-200 bg-white py-1 shadow-lg dark:border-gray-700 dark:bg-gray-800">
                  <button
                    type="button"
                    onClick={handleProfile}
                    className="w-full px-4 py-2 text-left text-sm text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-700"
                  >
                    Profile
                  </button>

                  <div className="my-1 border-t border-gray-100 dark:border-gray-700" />

                  <button
                    type="button"
                    onClick={handleLogout}
                    className="w-full px-4 py-2 text-left text-sm text-red-600 hover:bg-gray-100 dark:text-red-400 dark:hover:bg-gray-700"
                  >
                    Logout
                  </button>
                </div>
              )}
            </div>
          </>
        ) : (
          <button
            type="button"
            onClick={() => navigate("/login")}
            className="text-right"
          >
            <p className="text-sm font-semibold text-gray-900 hover:underline dark:text-white">
              Sign in
            </p>

            <p className="text-xs text-gray-500 dark:text-gray-400">
              Employee account
            </p>
          </button>
        )}
      </div>
    </header>
  );
}

export default Header;

import { Menu } from "lucide-react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

import { useAuth } from "../../context/AuthContext";
import { Sheet, SheetContent, SheetTitle, SheetTrigger } from "../ui/sheet";
import Sidebar from "./Sidebar";
import ThemeToggle from "./ThemeToggle";

function Header() {
  const { employee, isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const [showUserMenu, setShowUserMenu] = useState(false);
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

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
    <header className="fixed inset-x-0 top-0 z-50 flex h-16 items-center justify-between gap-3 border-b border-gray-200 bg-white px-4 sm:sticky sm:top-0 sm:z-40 sm:w-full sm:shrink-0 dark:border-gray-800 dark:bg-gray-900">
      <div className="flex min-w-0 items-center gap-3">
        {isAuthenticated && (
          <div className="xl:hidden">
            <Sheet open={isMobileMenuOpen} onOpenChange={setIsMobileMenuOpen}>
              <SheetTrigger
                className="inline-flex size-10 items-center justify-center rounded-lg border border-gray-200 hover:bg-gray-100 dark:border-gray-700 dark:hover:bg-gray-800"
                aria-label="Open navigation menu"
              >
                <Menu className="size-5" />
              </SheetTrigger>

              <SheetContent
                side="left"
                className="w-72 p-0"
                onClick={(event) => {
                  if ((event.target as HTMLElement).closest("a")) {
                    setIsMobileMenuOpen(false);
                  }
                }}
              >
                <SheetTitle className="sr-only">SupaBank navigation</SheetTitle>
                <Sidebar />
              </SheetContent>
            </Sheet>
          </div>
        )}

        <h1 className="truncate text-lg font-semibold text-gray-900 dark:text-white">
          SupaBank
        </h1>
      </div>
      <div className="flex shrink-0 items-center gap-2 sm:gap-4">
        <ThemeToggle />

        {isAuthenticated && employee ? (
          <>
            <div className="hidden text-right sm:block">
              <p className="text-sm font-semibold text-gray-900 dark:text-white">
                {employee.firstName} {employee.lastName}
              </p>
              <p className="text-xs text-gray-500 dark:text-gray-400">
                {employee.role}
              </p>
            </div>

            <div className="relative">
              <button
                type="button"
                onClick={() => setShowUserMenu((current) => !current)}
                aria-label="Open user menu"
                aria-expanded={showUserMenu}
                className="flex size-10 items-center justify-center rounded-full bg-gray-200 text-sm font-semibold text-gray-700 transition-colors hover:bg-gray-300 dark:bg-gray-700 dark:text-white dark:hover:bg-gray-600"
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
            <span className="text-sm font-semibold text-gray-900 hover:underline dark:text-white">
              Sign in
            </span>
          </button>
        )}
      </div>
    </header>
  );
}

export default Header;

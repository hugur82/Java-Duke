import ThemeToggle from "./ThemeToggle";

function Header() {
  return (
    <header className="flex h-16 items-center justify-between border-b border-gray-200 bg-white px-6 dark:border-gray-800 dark:bg-gray-900">
      {/* Page title */}
      <div>
        <h1 className="text-lg font-semibold text-gray-900 dark:text-white">
          Dashboard
        </h1>
      </div>

      {/* User section */}
      <div className="flex items-center gap-4  dark:text-white">
        <ThemeToggle />

        <div className="text-right">
          <p className="text-sm font-semibold text-gray-900 dark:text-white">
            Employee
          </p>

          <p className="text-xs text-gray-500 dark:text-gray-400">
            Employee account
          </p>
        </div>

        <div className="flex h-10 w-10 items-center justify-center rounded-full bg-gray-200 text-sm font-semibold text-gray-700 dark:bg-gray-700 dark:text-white">
          E
        </div>
      </div>
    </header>
  );
}

export default Header;

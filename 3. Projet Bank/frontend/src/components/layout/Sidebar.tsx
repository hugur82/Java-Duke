import { NavLink } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

function Sidebar() {
  const { employee } = useAuth();

  const navLinkClass = ({ isActive }: { isActive: boolean }) =>
    `flex items-center rounded-lg px-4 py-3 text-sm font-medium transition-colors ${
      isActive
        ? "bg-gray-100 text-gray-900 dark:bg-gray-800 dark:text-white"
        : "text-gray-600 hover:bg-gray-100 hover:text-gray-900 dark:text-gray-300 dark:hover:bg-gray-800 dark:hover:text-white"
    }`;

  return (
    <aside className="flex h-screen w-64 flex-col border-r border-gray-200 bg-white dark:border-gray-800 dark:bg-gray-900">
      <div className="flex h-16 items-center border-b border-gray-200 px-6 dark:border-gray-800">
        <h1 className="text-xl font-bold text-gray-900 dark:text-white">
          SupaBank
        </h1>
      </div>

      <nav className="flex-1 px-4 py-6">
        <ul className="space-y-2">
          <li>
            <NavLink to="/" className={navLinkClass}>
              Dashboard
            </NavLink>
          </li>

          <li>
            <NavLink to="/customers" className={navLinkClass}>
              Customers
            </NavLink>
          </li>

          <li>
            <NavLink to="/accounts" className={navLinkClass}>
              Accounts
            </NavLink>
          </li>

          <li>
            <NavLink to="/transactions" className={navLinkClass}>
              Transactions
            </NavLink>
          </li>

          {employee?.role === "ADMIN" && (
            <li>
              <NavLink to="/employees" className={navLinkClass}>
                Employees
              </NavLink>
            </li>
          )}

          <li>
            <NavLink to="/profile" className={navLinkClass}>
              Profile
            </NavLink>
          </li>
        </ul>
      </nav>
    </aside>
  );
}

export default Sidebar;

import { Navigate, Outlet, useLocation } from "react-router-dom";

import { useAuth } from "../../context/AuthContext";
import Header from "../layout/Header";
import Sidebar from "../layout/Sidebar";

function ProtectedLayout() {
  const { isAuthenticated, isLoading } = useAuth();
  const location = useLocation();

  if (isLoading) {
    return (
      <main className="flex min-h-screen items-center justify-center bg-gray-50 dark:bg-gray-950">
        <p className="text-gray-600 dark:text-gray-400">Loading...</p>
      </main>
    );
  }

  if (!isAuthenticated) {
    return <Navigate to="/login" replace state={{ from: location.pathname }} />;
  }

  return (
    <div className="flex min-h-screen bg-gray-50 dark:bg-gray-950">
      {/* Permanent sidebar: desktop only */}
      <aside className="fixed left-0 top-0 z-40 hidden h-screen shrink-0 xl:flex">
        <Sidebar />
      </aside>

      {/* Main content */}
      <div className="flex min-w-0 flex-1 flex-col xl:ml-48">
        <Header />
        <main className="min-w-0 flex-1 overflow-x-auto p-4 pt-20 sm:p-6">
          <Outlet />
        </main>
      </div>
    </div>
  );
}

export default ProtectedLayout;

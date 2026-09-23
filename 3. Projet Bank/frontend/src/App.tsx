import { BrowserRouter, Route, Routes } from "react-router-dom";

import Header from "./components/layout/Header";
import Sidebar from "./components/layout/Sidebar";
import AccountDetails from "./pages/AccountDetails";
import Accounts from "./pages/Accounts";
import CustomerDetails from "./pages/CustomerDetails";
import Customers from "./pages/Customers";
import Dashboard from "./pages/Dashboard";
import Profile from "./pages/Profile";
import TransactionDetails from "./pages/TransactionDetails";
import Transactions from "./pages/Transactions";

function App() {
  return (
    <BrowserRouter>
      <div className="flex h-screen bg-gray-50 dark:bg-gray-950">
        <Sidebar />

        <div className="flex min-w-0 flex-1 flex-col">
          <Header />

          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/customers" element={<Customers />} />
            <Route path="/customers/:id" element={<CustomerDetails />} />
            <Route path="/accounts" element={<Accounts />} />
            <Route path="/accounts/:id" element={<AccountDetails />} />
            <Route path="/transactions" element={<Transactions />} />
            <Route path="/transactions/:id" element={<TransactionDetails />} />
            <Route path="/profile" element={<Profile />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;

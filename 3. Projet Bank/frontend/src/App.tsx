import { BrowserRouter, Route, Routes } from "react-router-dom";
import ProtectedLayout from "./components/auth/ProtectedLayout";
import AccountDetails from "./pages/AccountDetails";
import Accounts from "./pages/Accounts";
import CustomerDetails from "./pages/CustomerDetails";
import Customers from "./pages/Customers";
import Dashboard from "./pages/Dashboard";
import EmployeeDetails from "./pages/EmployeeDetails";
import Employees from "./pages/Employees";
import Login from "./pages/Login";
import Profile from "./pages/Profile";
import TransactionDetails from "./pages/TransactionDetails";
import Transactions from "./pages/Transactions";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />

        <Route element={<ProtectedLayout />}>
          <Route path="/" element={<Dashboard />} />

          <Route path="/customers" element={<Customers />} />

          <Route path="/customers/:id" element={<CustomerDetails />} />

          <Route path="/accounts" element={<Accounts />} />

          <Route path="/accounts/:id" element={<AccountDetails />} />

          <Route path="/transactions" element={<Transactions />} />

          <Route path="/transactions/:id" element={<TransactionDetails />} />

          <Route path="/employees" element={<Employees />} />

          <Route path="/employees/:id" element={<EmployeeDetails />} />

          <Route path="/profile" element={<Profile />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;

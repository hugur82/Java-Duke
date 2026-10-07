import {
  createContext,
  useContext,
  useEffect,
  useState,
  type ReactNode,
} from "react";

import { clearStoredCredentials, getStoredCredentials } from "../api/apiClient";

import {
  login as loginApi,
  logout as logoutApi,
  type CurrentEmployee,
} from "../api/authApi";

interface AuthContextType {
  employee: CurrentEmployee | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
  updateEmployee: (employee: CurrentEmployee) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export function AuthProvider({ children }: AuthProviderProps) {
  const [employee, setEmployee] = useState<CurrentEmployee | null>(null);
  const updateEmployee = (updatedEmployee: CurrentEmployee) => {
    setEmployee(updatedEmployee);
  };
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const restoreSession = async () => {
      const credentials = getStoredCredentials();

      if (!credentials) {
        setIsLoading(false);
        return;
      }

      try {
        const response = await fetch("http://localhost:8080/api/auth/me", {
          headers: {
            Authorization:
              "Basic " +
              btoa(`${credentials.username}:${credentials.password}`),
          },
        });

        if (!response.ok) {
          clearStoredCredentials();
          setEmployee(null);
          return;
        }

        const employeeData: CurrentEmployee = await response.json();

        setEmployee(employeeData);
      } catch {
        clearStoredCredentials();
        setEmployee(null);
      } finally {
        setIsLoading(false);
      }
    };

    restoreSession();
  }, []);

  const login = async (username: string, password: string) => {
    const employeeData = await loginApi(username, password);
    setEmployee(employeeData);
  };

  const logout = () => {
    logoutApi();
    setEmployee(null);
  };

  return (
    <AuthContext.Provider
      value={{
        employee,
        isAuthenticated: employee !== null,
        isLoading,
        login,
        logout,
        updateEmployee,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used inside AuthProvider");
  }

  return context;
}

// @vitest-environment jsdom

import "@testing-library/jest-dom/vitest";
import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import Login from "../modules/Login";

const mockNavigate = vi.fn();
const mockUseAuth = vi.fn();

vi.mock("../contexts/AuthContext", () => ({
  useAuth: () => mockUseAuth(),
}));

vi.mock("react-router-dom", async () => {
  const actual = await vi.importActual("react-router-dom");
  return {
    ...actual,
    useNavigate: () => mockNavigate,
  };
});

describe("Login", () => {
  beforeEach(() => {
    mockNavigate.mockReset();
    mockUseAuth.mockReset();

    mockUseAuth.mockReturnValue({
      user: null,
      login: vi.fn(),
    });
  });

  it("rendert Benutzername, Passwort und Login-Button", () => {
    render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    );

    expect(screen.getByLabelText("Username:")).toBeInTheDocument();
    expect(screen.getByLabelText("Passwort:")).toBeInTheDocument();
    expect(screen.getByRole("button", { name: "Login" })).toBeInTheDocument();
  });

  it("zeigt das Login-Formular an, wenn kein Benutzer angemeldet ist", () => {
    render(
      <MemoryRouter>
        <Login />
      </MemoryRouter>
    );

    expect(screen.getAllByText("Login")[0]).toBeInTheDocument();
    expect(screen.getByLabelText("Username:")).toHaveValue("");
    expect(screen.getByLabelText("Passwort:")).toHaveValue("");
  });
});
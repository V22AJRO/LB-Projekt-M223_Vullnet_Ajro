// @vitest-environment jsdom

import "@testing-library/jest-dom/vitest";
import { describe, it, expect, vi, beforeEach } from "vitest";
import { render, screen } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import AdminRoute from "../components/AdminRoute";

const mockUseAuth = vi.fn();

vi.mock("../contexts/AuthContext", () => ({
  useAuth: () => mockUseAuth(),
}));

describe("AdminRoute", () => {
  beforeEach(() => {
    mockUseAuth.mockReset();
  });

  it("leitet ohne Benutzer auf Login um", () => {
    mockUseAuth.mockReturnValue({
      user: null,
    });

    render(
      <MemoryRouter>
        <AdminRoute>
          <div>Admin Inhalt</div>
        </AdminRoute>
      </MemoryRouter>
    );

    expect(screen.queryByText("Admin Inhalt")).not.toBeInTheDocument();
  });

  it("leitet normalen Benutzer auf Startseite um", () => {
    mockUseAuth.mockReturnValue({
      user: {
        username: "user1",
        roles: ["ROLE_USER"],
      },
    });

    render(
      <MemoryRouter>
        <AdminRoute>
          <div>Admin Inhalt</div>
        </AdminRoute>
      </MemoryRouter>
    );

    expect(screen.queryByText("Admin Inhalt")).not.toBeInTheDocument();
  });

  it("zeigt Inhalt für Admin an", () => {
    mockUseAuth.mockReturnValue({
      user: {
        username: "admin",
        roles: ["ROLE_ADMIN"],
      },
    });

    render(
      <MemoryRouter>
        <AdminRoute>
          <div>Admin Inhalt</div>
        </AdminRoute>
      </MemoryRouter>
    );

    expect(screen.getByText("Admin Inhalt")).toBeInTheDocument();
  });
});
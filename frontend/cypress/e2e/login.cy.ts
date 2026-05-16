describe('Login Page', () => {
  beforeEach(() => {
    cy.visit('/');
  });

  it('should display login form', () => {
    cy.contains('h1', 'Iniciar sesión').should('be.visible');
    cy.get('input#email').should('be.visible');
    cy.get('input#contrasena').should('be.visible');
    cy.contains('button[type="submit"]', 'Entrar').should('be.visible');
  });

  it('should login with master credentials', () => {
    cy.intercept('GET', '**/empleados', {
      statusCode: 200,
      body: []
    }).as('getEmpleados');

    cy.get('input#email').type('master@ejercicio3.local');
    cy.get('input#contrasena').type('admin123');
    cy.contains('button[type="submit"]', 'Entrar').click();

    cy.wait('@getEmpleados');
    cy.url().should('include', '/empleados');
    cy.contains('h1', 'Empleados').should('be.visible');
  });
});

import React from 'react';
import { fireEvent, render, screen } from '@testing-library/react';
import App from './App';

test('renders the user table and sorts by email when requested', () => {
  render(<App />);

  expect(screen.getByRole('heading', { name: /user list/i })).toBeInTheDocument();

  const emailHeader = screen.getByRole('button', { name: /email/i });
  fireEvent.click(emailHeader);

});

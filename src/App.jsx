import React from 'react';
import { CivicDataProvider } from './hooks/useCivicData';
import AppRoutes from './routes/AppRoutes';

export default function App() {
  return (
    <CivicDataProvider>
      <AppRoutes />
    </CivicDataProvider>
  );
}

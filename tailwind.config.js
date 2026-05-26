/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        themeLight: {
          bg: '#F7F5FC',
          card: 'rgba(255, 255, 255, 0.7)',
          border: 'rgba(255, 255, 255, 0.45)',
          glow: 'rgba(167, 139, 250, 0.15)',
          textMain: '#2E2543',
          textSub: '#645B7E',
          divider: 'rgba(100, 91, 126, 0.08)'
        },
        brand: {
          violet: {
            light: '#C4B5FD',
            DEFAULT: '#8B5CF6',
            dark: '#6D28D9',
          },
          purple: {
            light: '#E9D5FF',
            DEFAULT: '#A855F7',
            dark: '#7E22CE',
          },
          accent: {
            pink: '#EC4899',
            indigo: '#6366F1',
          }
        }
      },
      boxShadow: {
        'soft': '0 8px 32px 0 rgba(100, 91, 126, 0.05)',
        'premium': '0 10px 40px -10px rgba(139, 92, 246, 0.12)',
        'glow-violet': '0 0 20px rgba(139, 92, 246, 0.25)',
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
        outfit: ['Outfit', 'sans-serif'],
      },
      animation: {
        'float': 'float 6s ease-in-out infinite',
      },
      keyframes: {
        float: {
          '0%, 100%': { transform: 'translateY(0)' },
          '50%': { transform: 'translateY(-10px)' },
        }
      }
    },
  },
  plugins: [],
}

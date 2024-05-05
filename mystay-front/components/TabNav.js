
import ProfileScreen from '../screens/ProfileScreen';
import TransportScreen from '../screens/TransportScreen';
import CafeteriaScreen from '../screens/CafeteriaScreen';
import PremiumScreen from '../screens/PremiumScreen';

// Hidden screens
import CheckOutScreen from '../screens/CheckOutScreen';
import NewReserveScreen from '../screens/NewReserveScreen';
import ComfortServiceScreen from '../screens/ComfortServiceScreen';
import PayScreen from '../screens/PayScreen';
import EditProfileScreen from '../screens/EditProfileScreen';

import { MaterialCommunityIcons } from '@expo/vector-icons';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import IncidentScreen from '../screens/IncidentScreen';
import OtherServicesScreen from '../screens/OtherServicesScreen';
import ViewReservesScreen from '../screens/ViewReservesScreen';


const Tab = createBottomTabNavigator();

const TabNavigator = () => {
    return (
      <Tab.Navigator
        screenOptions={({ route }) => ({
          headerShown: false,
          tabBarShowLabel: false,
          tabBarIcon: ({ color }) => {
            let iconName;
      
            if (route.name === 'Profile') {
              iconName = 'account';
            } else if (route.name === 'Transport') {
              iconName = 'bus';
            } else if (route.name === 'Cafeteria') {
              iconName = 'food';
            } else {
              iconName = 'star';
            }
      
            return <MaterialCommunityIcons name={iconName} size={24} color={color} />;
          },
          tabBarActiveBackgroundColor: "#1d2b42",
          tabBarActiveTintColor: "white",
  
          tabBarInactiveTintColor: "#1d2b42",
          tabBarInactiveBackgroundColor: "white"
        })}
      >
        <Tab.Screen
          name="Profile"
          component={ProfileScreen}
          options={{
            tabBarAccessibilityLabel: "Profile"
          }}
        />
        <Tab.Screen 
          name="Transport"
          component={TransportScreen} 
          options={{
            tabBarAccessibilityLabel: "Transport"
          }}
        />
        <Tab.Screen
          name="Cafeteria"
          component={CafeteriaScreen}
          options={{
            tabBarAccessibilityLabel: "Cafeteria"
          }}
        />
        <Tab.Screen
          name="Premium"
          component={PremiumScreen}
          options={{
            tabBarAccessibilityLabel: "Premium"
          }}
        />
        
        <Tab.Screen name="(checkout)" options={{ tabBarButton: () => {} }} component={CheckOutScreen} />
        <Tab.Screen name="(reserve)" options={{ tabBarButton: () => {} }} component={NewReserveScreen} />
        <Tab.Screen name="(comfort)" options={{ tabBarButton: () => {} }} component={ComfortServiceScreen} />
        <Tab.Screen name="(incidents)" options={{ tabBarButton: () => {} }} component={IncidentScreen} />
        <Tab.Screen name="(services)" options={{ tabBarButton: () => {} }} component={OtherServicesScreen} />
        <Tab.Screen name="(pay)" options={{ tabBarButton: () => {} }} component={PayScreen} />
        <Tab.Screen name="(profile)" options={{ tabBarButton: () => {} }} component={EditProfileScreen} />
        <Tab.Screen name="(reserves)" options={{ tabBarButton: () => {} }} component={ViewReservesScreen} />
      
      </Tab.Navigator>
    );
  }
  
export default TabNavigator;  
/* $Id$ */
/***************************************************************************
 *                      (C) Copyright 2003 - Marauroa                      *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server;

import games.stendhal.common.CRC;
import games.stendhal.common.CollisionDetection;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.SheepFood;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.Portal;
import games.stendhal.server.entity.OneWayPortal;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.entity.npc.NPC;
import games.stendhal.server.rule.EntityManager;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

import marauroa.common.Log4J;
import marauroa.common.game.AttributeNotFoundException;
import marauroa.common.game.RPObject;
import marauroa.common.game.RPObjectInvalidException;
import marauroa.common.net.TransferContent;
import marauroa.server.game.MarauroaRPZone;
import marauroa.common.game.IRPZone;

import org.apache.log4j.Logger;

public class StendhalRPZone extends MarauroaRPZone
  {
  /** the logger instance. */
  private static final Logger logger = Log4J.getLogger(StendhalRPZone.class);
  /** the world */
  private StendhalRPWorld world;
  
  private List<TransferContent> contents;

  private List<String> entryPoints;
  private List<String> zoneChangePoints;
  private List<Portal> portals;

  private List<NPC> npcs;
  private List<RespawnPoint> respawnPoints;
  private List<SheepFood> foodItems;

  /** contains data to if a certain area is walkable*/
  public CollisionDetection collisionMap;

  /** Position of this zone in the world map */
  private boolean interior;
  private int level;
  private int x;
  private int y;
  
  /** contains navigation point nodes and 'streets' */
  public NavigationMap navigationMap;  

  public StendhalRPZone(String name, StendhalRPWorld world)
    {
    super(name);
    
    this.world = world;
    
    contents=new LinkedList<TransferContent>();
    entryPoints=new LinkedList<String>();
    zoneChangePoints=new LinkedList<String>();
    portals=new LinkedList<Portal>();

    npcs=new LinkedList<NPC>();
    respawnPoints=new LinkedList<RespawnPoint>();
    foodItems=new LinkedList<SheepFood>();
    
    collisionMap=new CollisionDetection();
    }
  
  public void onInit() throws Exception
    {
    }
    
  public void onFinish() throws Exception
    {
    }
  
  public StendhalRPWorld getWorld()
  {
    return world;
  }
  
  public List<NPC> getNPCList()
    {
    return npcs;
    }
  
  public List<Portal> getPortals()
    {
    return portals;
    }
  
  public Portal getPortal(int number)
    {
    for(Portal portal: portals)
      {
      if(portal.getNumber()==number)
        {
        return portal;
        }
      }
    
    return null;
    }

  public List<RespawnPoint> getRespawnPointList()
    {
    return respawnPoints;
    }

  public List<SheepFood> getFoodItemList()
    {
    return foodItems;
    }
    
  public void addZoneChange(String entry)
    {
    zoneChangePoints.add(entry);
    }  
  
  public void addPortal(Portal portal)
    {
    portals.add(portal);
    }
  
  public int assignPortalID(Portal portal)
    {
    // We reserve the first 64 portals ids for hand made portals
    int max=64;
    
    for(Portal p: portals)
      {
      if(p.getNumber()>max)
        {
        max=p.getNumber();
        }
      }
    
    portal.setNumber(max+1);
    
    return portal.getNumber();
    }

  public void addNPC(NPC npc)
    {
    npcs.add(npc);
    }

  public void addEntryPoint(String entry)
    {
    entryPoints.add(0,entry);
    }  
  
  public void placeObjectAtEntryPoint(Entity object)
    {
    if(entryPoints.size()==0)
      {
      return;
      }
      
    String entryPoint=entryPoints.get(0);
    String[] components=entryPoint.split(",");

    object.setx(Integer.parseInt(components[0]));
    object.sety(Integer.parseInt(components[1]));
    }

  public void placeObjectAtZoneChangePoint(StendhalRPZone oldzone, Entity object)
    {
    if(zoneChangePoints.size()==0)
      {
      return;
      }
      
    String exitDirection=null;

    if(object.gety()<4)
      {
      exitDirection="N";
      }
    else if(object.gety()>oldzone.getHeight()-4)
      {
      exitDirection="S";
      }
    else if(object.getx()<4)
      {
      exitDirection="W";
      }
    else if(object.getx()>oldzone.getWidth()-4)
      {
      exitDirection="E";
      }
    else
      {
      // NOTE: If any of the above is true, then it just put object on the first zone change point.
      String[] components=zoneChangePoints.get(0).split(",");
      logger.debug("Player zone change default: "+components);
      object.setx(Integer.parseInt(components[0]));
      object.sety(Integer.parseInt(components[1]));
      return;
      }
    
    logger.debug("Player exit direction: "+exitDirection);
    
    int x=0;
    int y=0;
    int distance=Integer.MAX_VALUE;
    String minpoint=zoneChangePoints.get(0);
    
    if(exitDirection.equals("N"))
      {
      x=object.getx();
      y=getHeight();
      }
    else if(exitDirection.equals("S"))
      {
      x=object.getx();
      y=0;
      }
    else if(exitDirection.equals("W"))
      {
      x=getWidth();
      y=object.gety();
      }
    else if(exitDirection.equals("E"))
      {
      x=0;
      y=object.gety();
      }

    logger.debug("Player entry point: ("+x+","+y+")");

    for(String point: zoneChangePoints)
      {
      String[] components=point.split(",");
      int px=Integer.parseInt(components[0]);
      int py=Integer.parseInt(components[1]);
      
      if((px-x)*(px-x)+(py-y)*(py-y)<distance)
        {
        logger.debug("Best entry point: ("+px+","+py+") --> "+distance);
        distance=(px-x)*(px-x)+(py-y)*(py-y);
        minpoint=point;
        }
      }
      
    logger.debug("Choosen entry point: ("+minpoint+") --> "+distance);
    String[] components=minpoint.split(",");
    object.setx(Integer.parseInt(components[0]));
    object.sety(Integer.parseInt(components[1]));
    }
  
  
  public void addLayer(String name, String byteContents) throws IOException
    {
    Log4J.startMethod(logger,"addLayer");
    TransferContent content=new TransferContent();
    content.name=name;
    content.cacheable=true;
    content.data=byteContents.getBytes();
    content.timestamp=CRC.cmpCRC(content.data);
    
    contents.add(content);
    Log4J.finishMethod(logger,"addLayer");
    }

  public void addCollisionLayer(String name, String byteContents) throws IOException
    {
    Log4J.startMethod(logger,"addCollisionLayer");
    TransferContent content=new TransferContent();
    content.name=name;
    content.cacheable=true;
    logger.debug("Layer timestamp: "+Integer.toString(content.timestamp));
    content.data=byteContents.getBytes();
    content.timestamp=CRC.cmpCRC(content.data);
    
    contents.add(content);
    
    collisionMap.setCollisionData(new StringReader(byteContents));//new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename)));
    Log4J.finishMethod(logger,"addCollisionLayer");
    }
  
  public void setPosition(int level, int x,int y)
    {
    this.interior=false;
    this.level=level;
    this.x=x;    
    this.y=y;
    }

  public void setPosition()
    {
    this.interior=true;
    }
  
  public int getx()
    {
    return x;
    }

  public int gety()
    {
    return y;
    }

  public int getLevel()
    {
    return level;
    }

  public boolean isInterior()
    {
    return interior;
    }
  
  public boolean contains(Entity player, int level, int player_x, int player_y)
    {
    Rectangle2D area=player.getArea(player_x,player_y);
    Rectangle2D zone=new Rectangle(x,y,getWidth(),getHeight());
    
    return zone.intersects(area);
    }

  public boolean contains(Entity entity, StendhalRPZone zone)
    {
    Rectangle2D area=entity.getArea(entity.getx()+zone.x,entity.gety()+zone.y);
    Rectangle2D zonearea=new Rectangle(x,y,getWidth(),getHeight());
    
    return zonearea.intersects(area);
    }
  
  public void addNavigationLayer(String name, String byteContents) throws IOException
    {
    Log4J.startMethod(logger,"addNavigationLayer");
   
    if(byteContents==null)    
      {
      logger.info("No navigation map for "+name+" found.");
      return;
      }
      
    try
      {
      navigationMap = new NavigationMap();
      navigationMap.setNavigationPoints(new StringReader(byteContents));
      return;
      }
    catch (IOException fnfe)
      {
      logger.info("No navigation map for "+name+" found.", fnfe);
      }    
      
    Log4J.finishMethod(logger,"addNavigationLayer");
    }  
  
  public void populate(String byteContents) throws IOException, RPObjectInvalidException
    {
    Log4J.startMethod(logger,"populate");
    
    BufferedReader file=new BufferedReader(new StringReader(byteContents));
    
    String text=file.readLine();
    String[] size=text.split(" ");
    int width=Integer.parseInt(size[0]);
    
    int j=0;
    
    while((text=file.readLine())!=null)
      {
      if(text.trim().equals(""))
        {
        break;
        }
        
      String[] items=text.split(",");
      for(String item: items)
        {
        int value=Integer.parseInt(item)-(2401) /* Number of tiles at zelda_outside_chipset */;
        createEntityAt(value,j%width,j/width);
        j++;      
        }
      }

    Log4J.finishMethod(logger,"populate");
    }
  
  protected void createEntityAt(int type, int x, int y)
    {
    try
      {
      switch(type)
        {
        case 1: /* Entry point */
          {
          String entryPoint=new String(x+","+y);
          addEntryPoint(entryPoint);
          break;
          }            
        case 2: /* Zone change  */
          {
          String entryPoint=new String(x+","+y);
          addZoneChange(entryPoint);
          break;
          }
        case 6: /* one way portal */
        case 3: /* portal stairs up  */
        case 4: /* portal stairs down  */
          {
          logger.debug ("Portal stairs at "+this+": "+x+","+y);
          Portal portal;
          if(type!=6)
            {
            portal=new Portal();
            }
          else
            {
            portal=new OneWayPortal();
            }
            
          assignRPObjectID(portal);
          portal.setx(x);
          portal.sety(y);
          assignPortalID(portal);
          add(portal);
          addPortal(portal);

          boolean assigned=false;

          if(isInterior())
            {
            // The algo doesn't work on interiors
            return;                        
            }
          
          for(IRPZone i: world)
            {
            StendhalRPZone zone=(StendhalRPZone)i;
            
            if(zone.isInterior()==false && Math.abs(zone.getLevel()-getLevel())==1)
              {
              if(!zone.contains(portal,this))
                {
                continue;
                }

              logger.debug (zone+" contains "+portal);
                
              for(Portal target: zone.getPortals())
                {
                if(target.loaded())
                  {
                  logger.debug (target+ " already loaded");
                  continue;
                  }
                  
                logger.debug (target+ " isn't loaded");
                
                if(target.getx()+zone.getx()==portal.getx()+getx() && target.gety()+zone.gety()==portal.gety()+gety())
                  {
                  int source=portal.getNumber();//assignPortalID(portal);
                  int dest=zone.assignPortalID(target);

                  if(type!=6) 
                    {
                    portal.setDestination(zone.getID().getID(),dest);
                    }
                    
                  target.setDestination(getID().getID(),source);
                  
                  logger.debug ("Portals LINKED");
                  logger.debug (portal);
                  logger.debug (target);
                  assigned=true;
                  break;
                  }
                else
                  {
                  logger.debug ("can't assign because it is a different portal");
                  }
                }
              }
            }
          
          if(!assigned)
            {
            logger.debug(portal+ " has no destination");
            }
          }
          break;
        case 5: /* portal */
        case 7: /* door */
          break;
          
        case 11: /* sheep  */
          break;
        case 71: /* the npcs  */
        case 72:
        case 73:
        case 74:
        case 75:
        case 76:
        case 77:
        case 78:
        case 79:
        case 80:
          break;
        case 91: /* sign */
          break;
        case 92: /* SheepFood */
          {
          SheepFood food=new SheepFood();
          assignRPObjectID(food);
          food.setAmount(5);
          food.setx(x);
          food.sety(y);
          add(food);
  
          foodItems.add(food);
          break;
          }
        default:
          {
            if (type >= 0)
            {
              // get the default EntityManager
              EntityManager manager = world.getRuleManager().getEntityManager();

              // Is the entity a creature
              if (manager.isCreature(type))
              {
                Creature creature = manager.getCreature(type);
                RespawnPoint point = new RespawnPoint(x,y,2);
                point.set(this, creature,1);
                respawnPoints.add(point);
              }
              else
              {
                logger.warn("Unknown Entity (type: "+type+") at ("+x+","+y+") of "+getID()+" found");
              }
            }
          break;
          }
        }
      }
    catch(AttributeNotFoundException e)
      {
      logger.error("error creating entity "+type+" at ("+x+","+y+")",e);
      }
    }
    
  public int getWidth()
    {
    return collisionMap.getWidth();
    }
  
  public int getHeight()
    {
    return collisionMap.getHeight();
    }
  
  
  public List<TransferContent> getContents()
    {
    return contents;
    }
  
  public boolean leavesZone(Entity entity, double x, double y) throws AttributeNotFoundException  
    {
    Rectangle2D area=entity.getArea(x,y);
    return collisionMap.leavesZone(area);
    }
    
  public boolean simpleCollides(Entity entity, double x, double y) throws AttributeNotFoundException  
    {
    Rectangle2D area=entity.getArea(x,y);
    return collisionMap.collides(area);
    }
    
  public synchronized boolean collides(Entity entity, double x, double y) throws AttributeNotFoundException
    {
    Rectangle2D area=entity.getArea(x,y);
    
    if(collisionMap.collides(area)==false)
      {
      Rectangle2D otherarea=new Rectangle.Double();
      for(RPObject other: objects.values())
        {
        Entity otherEntity=(Entity)other;
        
        if(otherEntity.isCollisionable())
          {
          otherEntity.getArea(otherarea,otherEntity.getx(),otherEntity.gety());
          if(area.intersects(otherarea) && !entity.getID().equals(otherEntity.getID()))
            {
            return true;
            }
          }
        }

      return false;
      }
    else
      {
      return true;
      }
    }
  
  /** @return the entity at x,y or null if there is none */
  public synchronized Entity getEntityAt(double x, double y) throws AttributeNotFoundException
    {
    for(RPObject other: objects.values())
      {
      Entity otherEntity = (Entity) other;
      
      Rectangle2D rect = otherEntity.getArea(otherEntity.getx(),otherEntity.gety());
      if(rect.contains(x,y))
        {
        return otherEntity;
        }
      }
    return null;
    }
  
  public String toString()
    {
    return "zone "+zoneid+" at ("+x+","+y+")";
    }
  }

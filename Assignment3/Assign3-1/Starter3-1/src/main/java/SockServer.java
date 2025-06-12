import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.*;
import java.io.*;
import java.util.*;

public class SockServer {
  static Socket sock;
  static DataOutputStream os;
  static ObjectInputStream in;
  static int port = 8888;

  // In-memory storage for dad jokes
  static class Joke { int id; String text; double rating; }
  static List<Joke> jokes = new ArrayList<>();
  static int nextJokeId = 1;
  static Random rand = new Random();

  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Expected arguments: <port(int)>");
      System.exit(1);
    }
    try {
      port = Integer.parseInt(args[0]);
    } catch (NumberFormatException nfe) {
      System.out.println("[Port] must be an integer");
      System.exit(2);
    }
    try {
      ServerSocket serv = new ServerSocket(port);
      System.out.println("Server ready for connections on port " + port);
      while (true) {
        sock = serv.accept();
        in = new ObjectInputStream(sock.getInputStream());
        os = new DataOutputStream(sock.getOutputStream());
        boolean connected = true;
        while (connected) {
          String msg;
          try {
            msg = (String) in.readObject();
          } catch (Exception e) {
            connected = false;
            break;
          }
          JSONObject res = isValid(msg);
          if (res.has("ok")) { writeOut(res); continue; }
          JSONObject req = new JSONObject(msg);
          res = testField(req, "type");
          if (!res.getBoolean("ok")) { writeOut(noType(req)); continue; }
          String type = req.getString("type");
          switch (type) {
            case "echo": res = echo(req); break;
            case "add": res = add(req); break;
            case "addmany": res = addmany(req); break;
            case "tempconvert": res = temp(req); break;
            case "dadjoke": res = dadjoke(req); break;
            default: res = wrongType(req);
          }
          writeOut(res);
        }
        overandout();
      }
    } catch (Exception e) {
      e.printStackTrace();
      overandout();
    }
  }

  static JSONObject testField(JSONObject req, String key) {
    JSONObject res = new JSONObject();
    if (!req.has(key)) {
      res.put("ok", false);
      res.put("message", "Field " + key + " does not exist in request");
      return res;
    }
    return res.put("ok", true);
  }

  static JSONObject echo(JSONObject req) {
    System.out.println("Echo request: " + req);
    JSONObject res = testField(req, "data");
    if (res.getBoolean("ok")) {
      if (!(req.get("data") instanceof String)) {
        res.put("ok", false);
        res.put("message", "Field data needs to be of type: String");
        return res;
      }
      res.put("type", "echo");
      res.put("echo", "Here is your echo: " + req.getString("data"));
    }
    return res;
  }

  static JSONObject add(JSONObject req) {
    System.out.println("Add request: " + req);
    JSONObject r1 = testField(req, "num1"); if (!r1.getBoolean("ok")) return r1;
    JSONObject r2 = testField(req, "num2"); if (!r2.getBoolean("ok")) return r2;
    JSONObject res = new JSONObject(); res.put("ok", true); res.put("type", "add");
    try { res.put("result", req.getInt("num1") + req.getInt("num2")); }
    catch (JSONException e) {
      res.put("ok", false);
      res.put("message", "Field num1/num2 needs to be of type: int");
    }
    return res;
  }

  static JSONObject addmany(JSONObject req) {
    System.out.println("Add many request: " + req);
    JSONObject r = testField(req, "nums"); if (!r.getBoolean("ok")) return r;
    int sum = 0; JSONArray arr = req.getJSONArray("nums");
    for (int i = 0; i < arr.length(); i++) {
      try { sum += arr.getInt(i); }
      catch (JSONException e) {
        r.put("ok", false);
        r.put("message", "Values in array need to be ints");
        return r;
      }
    }
    JSONObject res = new JSONObject();
    res.put("ok", true);
    res.put("type", "addmany");
    res.put("result", sum);
    return res;
  }

  static JSONObject temp(JSONObject req) {
    System.out.println("Temp convert request: " + req);
    JSONObject res;
    res = testField(req, "value"); if (!res.getBoolean("ok")) return res;
    res = testField(req, "fromUnit"); if (!res.getBoolean("ok")) return res;
    res = testField(req, "toUnit"); if (!res.getBoolean("ok")) return res;
    double val;
    try { val = req.getDouble("value"); } catch (JSONException e) {
      res = new JSONObject(); res.put("ok", false);
      res.put("message", "Field value needs to be of type: double"); return res;
    }
    String from, to;
    try { from = req.getString("fromUnit"); } catch (JSONException e) {
      res = new JSONObject(); res.put("ok", false);
      res.put("message", "Field fromUnit needs to be of type: String"); return res;
    }
    try { to = req.getString("toUnit"); } catch (JSONException e) {
      res = new JSONObject(); res.put("ok", false);
      res.put("message", "Field toUnit needs to be of type: String"); return res;
    }
    double c;
    switch (from) {
      case "C": c = val; break;
      case "F": c = (val - 32) * 5.0/9.0; break;
      case "K": c = val - 273.15; break;
      default:
        res = new JSONObject(); res.put("ok", false);
        res.put("message", "Unsupported fromUnit: " + from);
        return res;
    }
    if (c < -20 || c > 400) {
      res = new JSONObject(); res.put("type", "tempconvert");
      res.put("ok", false); res.put("message", "value out of bounds");
      return res;
    }
    double outVal;
    switch (to) {
      case "C": outVal = c; break;
      case "F": outVal = c * 9.0/5.0 + 32; break;
      case "K": outVal = c + 273.15; break;
      default:
        res = new JSONObject(); res.put("ok", false);
        res.put("message", "Unsupported toUnit: " + to);
        return res;
    }
    res = new JSONObject();
    res.put("type", "tempconvert");
    res.put("ok", true);
    res.put("result", outVal);
    return res;
  }

  static JSONObject dadjoke(JSONObject req) {
    System.out.println("DadJoke request: " + req);
    JSONObject res = testField(req, "action"); if (!res.getBoolean("ok")) return res;
    String action = req.getString("action");
    switch (action) {
      case "add":    return dadjokeAdd(req);
      case "get":    return dadjokeGet(req);
      case "rate":   return dadjokeRate(req);
      default:
        res = new JSONObject(); res.put("ok", false);
        res.put("message", "Unsupported action: " + action);
        return res;
    }
  }

  static JSONObject dadjokeAdd(JSONObject req) {
    JSONObject res = testField(req, "joke"); if (!res.getBoolean("ok")) return res;
    String text = req.getString("joke");
    Joke jk = new Joke(); jk.id = nextJokeId++; jk.text = text; jk.rating = 5.0;
    jokes.add(jk);
    res = new JSONObject();
    res.put("type", "dadjoke");
    res.put("ok", true);
    res.put("message", "Joke added, this is joke number " + jk.id + "!");
    return res;
  }

  static JSONObject dadjokeGet(JSONObject req) {
    if (jokes.isEmpty()) {
      JSONObject res = new JSONObject();
      res.put("type", "dadjoke");
      res.put("ok", false);
      res.put("message", "No jokes on server");
      return res;
    }
    Joke jk = jokes.get(rand.nextInt(jokes.size()));
    JSONObject res = new JSONObject();
    res.put("type", "dadjoke");
    res.put("ok", true);
    res.put("jokeID", jk.id);
    res.put("joke", jk.text);
    res.put("rating", jk.rating);
    return res;
  }

  static JSONObject dadjokeRate(JSONObject req) {
    JSONObject r1 = testField(req, "jokeID"); if (!r1.getBoolean("ok")) return r1;
    JSONObject r2 = testField(req, "rating"); if (!r2.getBoolean("ok")) return r2;
    int id; double newRating;
    try { id = req.getInt("jokeID"); }
    catch (JSONException e) {
      JSONObject res = new JSONObject(); res.put("ok", false);
      res.put("message", "Field jokeID needs to be of type: int");
      return res;
    }
    try { newRating = Double.parseDouble(req.getString("rating")); }
    catch (Exception e) {
      JSONObject res = new JSONObject(); res.put("ok", false);
      res.put("message", "Field rating needs to be of type: String");
      return res;
    }
    if (newRating < 0 || newRating > 5) {
      JSONObject res = new JSONObject(); res.put("ok", false);
      res.put("message", "Rating is out of bounds"); return res;
    }
    for (Joke jk : jokes) {
      if (jk.id == id) {
        jk.rating = (jk.rating + newRating) / 2.0;
        JSONObject res = new JSONObject();
        res.put("type", "dadjoke");
        res.put("ok", true);
        res.put("jokeID", jk.id);
        res.put("joke", jk.text);
        res.put("rating", jk.rating);
        return res;
      }
    }
    JSONObject res = new JSONObject(); res.put("ok", false);
    res.put("message", "No joke with ID " + id);
    return res;
  }

  static JSONObject wrongType(JSONObject req) {
    JSONObject res = new JSONObject();
    res.put("ok", false);
    res.put("message", "Type " + req.optString("type") + " is not supported.");
    return res;
  }

  static JSONObject noType(JSONObject req) {
    JSONObject res = new JSONObject();
    res.put("ok", false);
    res.put("message", "No request type was given.");
    return res;
  }

  public static JSONObject isValid(String json) {
    try { new JSONObject(json); }
    catch (JSONException e) {
      try { new JSONArray(json); }
      catch (JSONException ne) {
        JSONObject res = new JSONObject(); res.put("ok", false);
        res.put("message", "req not JSON"); return res;
      }
    }
    return new JSONObject();
  }

  static void writeOut(JSONObject res) {
    try { os.writeUTF(res.toString()); os.flush(); } catch (Exception e) { e.printStackTrace(); }
  }

  static void overandout() {
    try { os.close(); in.close(); sock.close(); } catch (Exception e) { e.printStackTrace(); }
  }
}

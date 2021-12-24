package br.com.bank.domain.generic.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.criteria.internal.path.ListAttributeJoin;
import org.hibernate.query.criteria.internal.path.SingularAttributeJoin;
import org.springframework.data.jpa.domain.Specification;

public class GenericSpecification<Entity> {

   @SuppressWarnings("serial")
   public static <Entity> Specification<Entity> fetch(String[] juncoes) {      
      return new Specification<Entity>() {
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (juncoes != null && juncoes.length > 0) {
               for (int z = 0; z < juncoes.length; z++) {
                  Set fetches = root.getFetches();
                  List lista = null;

                  if (fetches != null && fetches.size() > 0) {
                     lista = new ArrayList(fetches);
                  }

                  String[] array = juncoes[z].split("\\.");
                  Join juncao = null;
                  JoinType type = null;

                  if (array.length == 1) {
                     if (juncoes[z].contains("*")) {
                        type = JoinType.LEFT;
                     } else {
                        type = JoinType.INNER;
                     }

                     String attribute = juncoes[z].replace("*", "");
                     root.fetch(attribute, type);
                  } else {
                     for (int i = 0; i < array.length; i++) {
                        boolean realizaFetch = true;

                        if (array[i].contains("*")) {
                           type = JoinType.LEFT;
                        } else {
                           type = JoinType.INNER;
                        }

                        String strJoin = array[i].replace("*", "");

                        // primeiro join

                        if (i == 0) {
                           if (lista != null && lista.size() > 0) {
                              for (Object attributeJoin : lista) {
                                 if (attributeJoin instanceof ListAttributeJoin) {
                                    if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                       juncao = (ListAttributeJoin) attributeJoin;
                                       realizaFetch = false;
                                    }
                                 } else {
                                    if (attributeJoin instanceof SingularAttributeJoin) {
                                       if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                             .equals(array[i])) {
                                          juncao = (SingularAttributeJoin) attributeJoin;
                                          realizaFetch = false;
                                       }
                                    }
                                 }
                              }
                           }
                        } else {
                           fetches = juncao.getFetches();

                           if (fetches != null && fetches.size() > 0) {
                              lista = new ArrayList(fetches);
                           }

                           if (lista != null && lista.size() > 0) {
                              for (Object attributeJoin : lista) {
                                 if (attributeJoin instanceof ListAttributeJoin) {
                                    if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                       juncao = (ListAttributeJoin) attributeJoin;
                                       realizaFetch = false;
                                    }
                                 } else {
                                    if (attributeJoin instanceof SingularAttributeJoin) {
                                       if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                             .equals(array[i])) {
                                          juncao = (SingularAttributeJoin) attributeJoin;
                                          realizaFetch = false;
                                       }
                                    }
                                 }
                              }
                           }
                        }

                        if (realizaFetch) {
                           if (juncao == null) {
                              juncao = (Join) root.fetch(strJoin, type);
                           } else {
                              juncao = (Join) juncao.fetch(strJoin, type);
                           }
                        }
                     }
                  }
               }
            }

            return null;
         }
      };
   }

   @SuppressWarnings("serial")
   public static <Entity> Specification<Entity> fetch(String join) {
      return new Specification<Entity>() {
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (join != null && !join.equals("")) {
               Set fetches = root.getFetches();
               List lista = null;

               if (fetches != null && fetches.size() > 0) {
                  lista = new ArrayList(fetches);
               }

               String[] array = join.split("\\.");
               Join juncao = null;
               JoinType type = null;

               if (array.length == 1) {
                  if (join.contains("*")) {
                     type = JoinType.LEFT;
                  } else {
                     type = JoinType.INNER;
                  }

                  String attribute = join.replace("*", "");
                  root.fetch(attribute, type);
               } else {
                  for (int i = 0; i < array.length; i++) {
                     boolean realizaFetch = true;

                     if (array[i].contains("*")) {
                        type = JoinType.LEFT;
                     } else {
                        type = JoinType.INNER;
                     }

                     String strJoin = array[i].replace("*", "");

                     // primeiro join

                     if (i == 0) {
                        if (lista != null && lista.size() > 0) {
                           for (Object attributeJoin : lista) {
                              if (attributeJoin instanceof ListAttributeJoin) {
                                 if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                    juncao = (ListAttributeJoin) attributeJoin;
                                    realizaFetch = false;
                                 }
                              } else {
                                 if (attributeJoin instanceof SingularAttributeJoin) {
                                    if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                          .equals(array[i])) {
                                       juncao = (SingularAttributeJoin) attributeJoin;
                                       realizaFetch = false;
                                    }
                                 }
                              }
                           }
                        }
                     } else {
                        fetches = juncao.getFetches();

                        if (fetches != null && fetches.size() > 0) {
                           lista = new ArrayList(fetches);
                        }

                        if (lista != null && lista.size() > 0) {
                           for (Object attributeJoin : lista) {
                              if (attributeJoin instanceof ListAttributeJoin) {
                                 if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                    juncao = (ListAttributeJoin) attributeJoin;
                                    realizaFetch = false;
                                 }
                              } else {
                                 if (attributeJoin instanceof SingularAttributeJoin) {
                                    if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                          .equals(array[i])) {
                                       juncao = (SingularAttributeJoin) attributeJoin;
                                       realizaFetch = false;
                                    }
                                 }
                              }
                           }
                        }
                     }

                     if (realizaFetch) {
                        if (juncao == null) {
                           juncao = (Join) root.fetch(strJoin, type);
                        } else {
                           juncao = (Join) juncao.fetch(strJoin, type);
                        }
                     }
                  }
               }
            }

            return null;
         }
      };
   }

   @SuppressWarnings("serial")
   public static <Entity> Specification<Entity> flike(final String filtro, final String valor) {
      return new Specification<Entity>() {
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (filtro != null && !filtro.equals("") && valor != null && !valor.toString().equals("")) {
               String[] array = filtro.split("\\.");

               if (array.length == 1) {
                  return cb.like(cb.lower(root.get(filtro)), "%" + valor.toLowerCase() + "%");
               } else {
                  Join join = null;
                  Set fetches = root.getFetches();
                  List lista = null;

                  if (fetches != null && fetches.size() > 0) {
                     lista = new ArrayList(fetches);
                  }

                  for (int i = 0; i < array.length; i++) {
                     if (i == 0) {
                        for (Object attributeJoin : lista) {
                           if (attributeJoin instanceof ListAttributeJoin) {
                              if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                 join = (ListAttributeJoin) attributeJoin;
                              }
                           } else {
                              if (attributeJoin instanceof SingularAttributeJoin) {
                                 if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                       .equals(array[i])) {
                                    join = (SingularAttributeJoin) attributeJoin;
                                 }
                              }
                           }

                        }
                     } else {
                        if (i != array.length - 1) {
                           fetches = join.getFetches();

                           if (fetches != null && fetches.size() > 0) {
                              lista = new ArrayList(fetches);
                           }

                           for (Object attributeJoin : lista) {
                              if (attributeJoin instanceof ListAttributeJoin) {
                                 if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                    join = (ListAttributeJoin) attributeJoin;
                                 }
                              } else {
                                 if (attributeJoin instanceof SingularAttributeJoin) {
                                    if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                          .equals(array[i])) {
                                       join = (SingularAttributeJoin) attributeJoin;
                                    }
                                 }
                              }
                           }
                        } else {
                           return cb.like(cb.lower(join.get(array[i])), "%" + valor.toLowerCase() + "%");
                        }
                     }
                  }
               }
            }

            return null;
         }
      };
   }

   @SuppressWarnings("serial")
   public static <Entity> Specification<Entity> notIn(final String filtro, final Object[] valor) {
      return new Specification<Entity>() {
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (filtro != null && !filtro.equals("") && valor != null && !valor.toString().equals("")) {
               String[] array = filtro.split("\\.");

               if (array.length == 1) {
                  return root.get(filtro).in(valor).not();
               } else {
                  Join join = null;
                  Set fetches = root.getFetches();
                  List lista = null;

                  if (fetches != null && fetches.size() > 0) {
                     lista = new ArrayList(fetches);
                  }

                  for (int i = 0; i < array.length; i++) {
                     if (i == 0) {
                        for (Object attributeJoin : lista) {
                           if (attributeJoin instanceof ListAttributeJoin) {
                              if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                 join = (ListAttributeJoin) attributeJoin;
                              }
                           } else {
                              if (attributeJoin instanceof SingularAttributeJoin) {
                                 if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                       .equals(array[i])) {
                                    join = (SingularAttributeJoin) attributeJoin;
                                 }
                              }
                           }

                        }
                     } else {
                        if (i != array.length - 1) {
                           fetches = join.getFetches();

                           if (fetches != null && fetches.size() > 0) {
                              lista = new ArrayList(fetches);
                           }

                           for (Object attributeJoin : lista) {
                              if (attributeJoin instanceof ListAttributeJoin) {
                                 if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                    join = (ListAttributeJoin) attributeJoin;
                                 }
                              } else {
                                 if (attributeJoin instanceof SingularAttributeJoin) {
                                    if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                          .equals(array[i])) {
                                       join = (SingularAttributeJoin) attributeJoin;
                                    }
                                 }
                              }
                           }
                        } else {
                           return join.get(array[i]).in(valor).not();
                        }
                     }
                  }
               }
            }

            return null;
         }
      };
   }

   @SuppressWarnings("serial")
   public static <Entity> Specification<Entity> igual(final String filtro, final Object valor) {
      return new Specification<Entity>() {
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (filtro != null && !filtro.equals("") && valor != null && !valor.toString().equals("")) {
               String[] array = filtro.split("\\.");

               if (array.length == 1) {
                  if (valor instanceof String) {
                     return cb.equal(cb.lower(root.get(filtro)), valor.toString().toLowerCase());
                  } else {
                     return cb.equal(root.get(filtro), valor);
                  }

               } else {
                  Join join = null;
                  Set fetches = root.getFetches();
                  List lista = null;

                  if (fetches != null && fetches.size() > 0) {
                     lista = new ArrayList(fetches);
                  }

                  for (int i = 0; i < array.length; i++) {
                     if (i == 0) {
                        for (Object attributeJoin : lista) {
                           if (attributeJoin instanceof ListAttributeJoin) {
                              if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                 join = (ListAttributeJoin) attributeJoin;
                              }
                           } else {
                              if (attributeJoin instanceof SingularAttributeJoin) {
                                 if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                       .equals(array[i])) {
                                    join = (SingularAttributeJoin) attributeJoin;
                                 }
                              }
                           }

                        }
                     } else {
                        if (i != array.length - 1) {
                           //
                           fetches = join.getFetches();

                           if (fetches != null && fetches.size() > 0) {
                              lista = new ArrayList(fetches);
                           }

                           for (Object attributeJoin : lista) {
                              if (attributeJoin instanceof ListAttributeJoin) {
                                 if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                    join = (ListAttributeJoin) attributeJoin;
                                 }
                              } else {
                                 if (attributeJoin instanceof SingularAttributeJoin) {
                                    if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                          .equals(array[i])) {
                                       join = (SingularAttributeJoin) attributeJoin;
                                    }
                                 }
                              }
                           }
                        } else {
                           if (valor instanceof String) {
                              return cb.equal(cb.lower(join.get(array[i])), valor.toString().toLowerCase());
                           } else {
                              return cb.equal(join.get(array[i]), valor);
                           }
                        }
                     }
                  }
               }
            }

            return null;
         }
      };
   }

   @SuppressWarnings("serial")
   public static <Entity> Specification<Entity> dataMaiorEq(final String filtro, final Date valor) {
      return new Specification<Entity>() {
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (filtro != null && !filtro.equals("") && valor != null && !valor.toString().equals("")) {
               String[] array = filtro.split("\\.");

               if (array.length == 1) {
                  return cb.greaterThanOrEqualTo(root.get(filtro).as(Date.class), valor);
               } else {
                  Join join = null;
                  Set fetches = root.getFetches();
                  List lista = null;

                  if (fetches != null && fetches.size() > 0) {
                     lista = new ArrayList(fetches);
                  }

                  for (int i = 0; i < array.length; i++) {
                     if (i == 0) {
                        for (Object attributeJoin : lista) {
                           if (attributeJoin instanceof ListAttributeJoin) {
                              if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                 join = (ListAttributeJoin) attributeJoin;
                              }
                           } else {
                              if (attributeJoin instanceof SingularAttributeJoin) {
                                 if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                       .equals(array[i])) {
                                    join = (SingularAttributeJoin) attributeJoin;
                                 }
                              }
                           }

                        }
                     } else {
                        if (i != array.length - 1) {
                           //
                           fetches = join.getFetches();

                           if (fetches != null && fetches.size() > 0) {
                              lista = new ArrayList(fetches);
                           }

                           for (Object attributeJoin : lista) {
                              if (attributeJoin instanceof ListAttributeJoin) {
                                 if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                    join = (ListAttributeJoin) attributeJoin;
                                 }
                              } else {
                                 if (attributeJoin instanceof SingularAttributeJoin) {
                                    if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                          .equals(array[i])) {
                                       join = (SingularAttributeJoin) attributeJoin;
                                    }
                                 }
                              }
                           }
                        } else {
                           return cb.greaterThanOrEqualTo(join.get(array[i]).as(Date.class), valor);
                        }
                     }
                  }
               }
            }

            return null;
         }
      };
   }

   @SuppressWarnings("serial")
   public static <Entity> Specification<Entity> dataMenorEq(final String filtro, final Date valor) {
      return new Specification<Entity>() {
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (filtro != null && !filtro.equals("") && valor != null && !valor.toString().equals("")) {
               String[] array = filtro.split("\\.");

               if (array.length == 1) {
                  return cb.lessThanOrEqualTo(root.get(filtro).as(Date.class), valor);
               } else {
                  Join join = null;
                  Set fetches = root.getFetches();
                  List lista = null;

                  if (fetches != null && fetches.size() > 0) {
                     lista = new ArrayList(fetches);
                  }

                  for (int i = 0; i < array.length; i++) {
                     if (i == 0) {
                        for (Object attributeJoin : lista) {
                           if (attributeJoin instanceof ListAttributeJoin) {
                              if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                 join = (ListAttributeJoin) attributeJoin;
                              }
                           } else {
                              if (attributeJoin instanceof SingularAttributeJoin) {
                                 if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                       .equals(array[i])) {
                                    join = (SingularAttributeJoin) attributeJoin;
                                 }
                              }
                           }

                        }
                     } else {
                        if (i != array.length - 1) {
                           fetches = join.getFetches();

                           if (fetches != null && fetches.size() > 0) {
                              lista = new ArrayList(fetches);
                           }

                           for (Object attributeJoin : lista) {
                              if (attributeJoin instanceof ListAttributeJoin) {
                                 if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                    join = (ListAttributeJoin) attributeJoin;
                                 }
                              } else {
                                 if (attributeJoin instanceof SingularAttributeJoin) {
                                    if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                          .equals(array[i])) {
                                       join = (SingularAttributeJoin) attributeJoin;
                                    }
                                 }
                              }
                           }
                        } else {
                           return cb.lessThanOrEqualTo(join.get(array[i]).as(Date.class), valor);
                        }
                     }
                  }
               }
            }

            return null;
         }
      };
   }

   @SuppressWarnings("serial")
   public static <Entity> Specification<Entity> order(final String[] atributos) {
      return new Specification<Entity>() {
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            if (atributos != null && atributos.length > 0) {
               List<Order> listaOrder = new ArrayList<Order>();

               for (int z = 0; z < atributos.length; z++) {
                  String[] array = atributos[z].split("\\.");

                  Order order = null;

                  if (array.length == 1) {
                     if (atributos[z].contains("*")) {
                        order = cb.desc(root.get(atributos[z].replace("*", "")));

                     } else {
                        order = cb.asc(root.get(atributos[z]));
                     }
                  } else {
                     Join join = null;
                     Set fetches = root.getFetches();
                     List lista = null;

                     if (fetches != null && fetches.size() > 0) {
                        lista = new ArrayList(fetches);
                     }

                     for (int i = 0; i < array.length; i++) {
                        if (i == 0) {
                           for (Object attributeJoin : lista) {
                              if (attributeJoin instanceof ListAttributeJoin) {
                                 if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                    join = (ListAttributeJoin) attributeJoin;
                                 }
                              } else {
                                 if (attributeJoin instanceof SingularAttributeJoin) {
                                    if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                          .equals(array[i])) {
                                       join = (SingularAttributeJoin) attributeJoin;
                                    }
                                 }
                              }

                           }
                        } else {
                           if (i != array.length - 1) {
                              fetches = join.getFetches();

                              if (fetches != null && fetches.size() > 0) {
                                 lista = new ArrayList(fetches);
                              }

                              for (Object attributeJoin : lista) {
                                 if (attributeJoin instanceof ListAttributeJoin) {
                                    if (((ListAttributeJoin) attributeJoin).getAttribute().getName().equals(array[i])) {
                                       join = (ListAttributeJoin) attributeJoin;
                                    }
                                 } else {
                                    if (attributeJoin instanceof SingularAttributeJoin) {
                                       if (((SingularAttributeJoin) attributeJoin).getAttribute().getName()
                                             .equals(array[i])) {
                                          join = (SingularAttributeJoin) attributeJoin;
                                       }
                                    }
                                 }
                              }
                           } else {
                              if (array[i].contains("*")) {
                                 order = cb.desc(join.get(array[i].replace("*", "")));
                              } else {
                                 order = cb.asc(join.get(array[i]));
                              }
                           }
                        }
                     }
                  }

                  listaOrder.add(order);
               }

               query.orderBy(listaOrder);
            }

            return null;
         }
      };
   }

}